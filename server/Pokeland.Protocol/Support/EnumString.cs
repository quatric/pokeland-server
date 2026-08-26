#nullable disable
using Newtonsoft.Json;

namespace Pokeland.Protocol;

/// <summary>
/// Wire wrapper that carries an enum as its *name* rather than its ordinal.
/// The game uses this (via <c>ISerializationCallbackReceiver</c>) so that adding
/// enum members server-side cannot renumber values an old client already holds.
/// </summary>
[JsonConverter(typeof(EnumStringConverter))]
public class EnumString<T> where T : struct, Enum
{
    public T Value { get; set; }

    public string Name
    {
        get => Value.ToString();
        set => Value = Enum.TryParse<T>(value, out var v) ? v : default;
    }

    public override string ToString() => Name;

    public static implicit operator T(EnumString<T> s) => s is null ? default : s.Value;
}

internal sealed class EnumStringConverter : JsonConverter
{
    public override bool CanConvert(Type t) =>
        t.BaseType is { IsGenericType: true } b &&
        b.GetGenericTypeDefinition() == typeof(EnumString<>);

    public override void WriteJson(JsonWriter w, object value, JsonSerializer s) =>
        w.WriteValue(value?.ToString());

    public override object ReadJson(JsonReader r, Type objectType, object existing, JsonSerializer s)
    {
        var inst = existing ?? Activator.CreateInstance(objectType);
        var prop = objectType.GetProperty("Name");
        prop!.SetValue(inst, r.Value?.ToString());
        return inst;
    }
}
