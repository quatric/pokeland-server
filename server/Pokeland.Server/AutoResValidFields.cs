#nullable disable
using System;
using System.Linq;
using System.Reflection;
using Newtonsoft.Json.Serialization;
using Pokeland.Protocol;

namespace Pokeland.Server;

public static class AutoResValidFields
{
    // name -> (bit, property). Built once; the generated protocol type never
    // changes at runtime.
    private static readonly (AutoResValidField Bit, PropertyInfo Property)[] Guarded =
        Enum.GetValues<AutoResValidField>()
            .Select(bit => (Bit: bit, Property: typeof(AutoRes).GetProperty(bit.ToString())))
            .Where(x => x.Property is not null)
            .ToArray();

    /// <summary>The mask for one AutoRes: a bit per populated guarded field.</summary>
    public static AutoResValidField Compute(AutoRes res)
    {
        var bits = res.ValidFields; // keep anything set by hand
        foreach (var (bit, property) in Guarded)
        {
            if (property.GetValue(res) is not null)
                bits |= bit;
        }
        return bits;
    }

    /// <summary>Stamp the mask onto the instance, just before it is written.</summary>
    public static void Apply(AutoRes res) => res.ValidFields = Compute(res);
}

/// <summary>
/// Applies the mask at serialization time rather than at each construction
/// site: handlers that build an empty <c>AutoRes</c> and fill it afterwards
/// would defeat a constructor-time hook, and <c>Proto.g.cs</c> is generated so
/// the callback cannot live on the type itself.
/// </summary>
public sealed class AutoResContractResolver : DefaultContractResolver
{
    protected override JsonObjectContract CreateObjectContract(Type objectType)
    {
        var contract = base.CreateObjectContract(objectType);
        if (objectType == typeof(AutoRes))
            contract.OnSerializingCallbacks.Add(
                (o, _) => AutoResValidFields.Apply((AutoRes)o));
        return contract;
    }
}
