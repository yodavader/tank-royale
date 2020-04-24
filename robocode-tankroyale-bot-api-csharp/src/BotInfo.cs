using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Threading;

namespace Robocode.TankRoyale.BotApi
{
  /// <summary>
  /// Required information about a bot.
  /// </summary>
  /// <value></value>
  public sealed class BotInfo
  {
    private string name;
    private string version;
    private string author;
    private IEnumerable<string> gameTypes;

    /// <summary>
    /// The name, e.g., "MyBot". This field must always be provided with the bot info.
    /// </summary>
    /// <value>The name of the bot.</value>
    public string Name
    {
      get => name;
      private set { if (value == null) { throw new NullReferenceException("Name cannot be null"); } }
    }

    /// <summary>
    /// The version, e.g., "1.0". This field must always be provided with the bot info.
    /// </summary>
    /// <value>The version of the bot.</value>
    public string Version
    {
      get => version;
      private set { if (value == null) { throw new NullReferenceException("Version cannot be null"); } }
    }

    /// <summary>
    /// The author, e.g., "John Doe (johndoe@somewhere.io)". This field must always be provided
    /// with the bot info.
    /// </summary>
    /// <value>The author of the bot.</value>
    public string Author
    {
      get => author;
      private set { if (value == null) { throw new NullReferenceException("Author cannot be null"); } }
    }

    /// <summary>
    /// Short description of the bot, preferably a one-liner. This field is optional.
    /// </summary>
    /// <value>A short description of the bot.</value>
    public string Description { get; private set; }

    /// <summary>
    /// The URL of a web page for the bot. This field is optional.
    /// </summary>
    /// <value>The URL of a web page for the bot.</value>
    public string Url { get; private set; }

    /// <summary>
    /// The country code defined by ISO 3166-1 alpha-2, e.g. "us":
    /// https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2.
    /// If no country code is provided, the locale of the system is being used instead.
    /// </summary>
    /// <value>The country code for the bot.</value>
    public string CountryCode { get; private set; }

    /// <summary>
    /// The game types accepted by the bot, e.g., "melee", "1v1". This field must always be
    /// provided with the bot info. The game types define which game types the bot can participate
    /// in. See <see cref="GameType"/> for using predefined game type.
    /// </summary>
    /// <value>The game types that this bot can handle.</value>
    public IEnumerable<string> GameTypes
    {
      get => gameTypes;
      private set { if (value == null) { throw new NullReferenceException("GameTypes cannot be null"); } }
    }

    /// <summary>
    /// The platform used for running the bot, e.g., "Java Runtime Environment" or ".Net Core".
    /// This field is optional.
    /// </summary>
    /// <value>The platform used for running the bot.</value>
    public string Platform { get; private set; }

    /// <summary>
    /// The programming language used for developing the bot, e.g., "Java" or "C#". Thisfield is
    /// optional.
    /// </summary>
    /// <value>The programming language used for developing the bot.</value>
    public string ProgrammingLang { get; private set; }

    /// <summary>
    /// Initializes a new instance of the BotInfo class.
    /// </summary>
    /// <param name="name">The name of the bot (required).</param>
    /// <param name="version">The version of the bot (required).</param>
    /// <param name="author">The author of the bot (required).</param>
    /// <param name="description">A short description of the bot (optional).</param>
    /// <param name="url">The URL to a web page for the bot (optional).</param>
    /// <param name="countryCode">The country code for the bot (optional).</param>
    /// <param name="gameTypes">The game types that this bot can handle (required).</param>
    /// <param name="platform">The platform used for running the bot (optional).</param>
    /// <param name="programmingLang">The programming language used for developing the bot (optional).</param>
    public BotInfo(
      string name,
      string version,
      string author,
      string description,
      string url,
      string countryCode,
      IEnumerable<string> gameTypes,
      string platform,
      string programmingLang)
    {
      if (string.IsNullOrWhiteSpace(name))
      {
        throw new ArgumentException("Name cannot be null, empty or blank");
      }
      if (string.IsNullOrWhiteSpace(version))
      {
        throw new ArgumentException("Version cannot be null, empty or blank");
      }
      if (string.IsNullOrWhiteSpace(author))
      {
        throw new ArgumentException("Author cannot be null, empty or blank");
      }
      if (gameTypes == null)
      {
        throw new ArgumentException("GameTypes cannot be null, empty or blank");
      }

      RegionInfo regionInfo = null;
      if (!string.IsNullOrWhiteSpace(countryCode))
      {
        // Get country code from input parameter
        try
        {
          regionInfo = new RegionInfo(countryCode.Trim());
        }
        catch (ArgumentException)
        {
          throw new ArgumentException($"CountryCode is not valid: '{countryCode}'");
        }
      }
      else
      {
        // Get local country code
        CultureInfo cultureInfo = Thread.CurrentThread.CurrentCulture;
        regionInfo = new RegionInfo(cultureInfo.Name);
      }

      var trimmedGameTypes = gameTypes.Where(s => !string.IsNullOrWhiteSpace(s)).Distinct().ToHashSet();
      if (trimmedGameTypes.Count == 0)
      {
        throw new ArgumentException("GameTypes does not contain any game types");
      }

      this.name = name;
      this.version = version;
      this.author = author;
      this.Description = description;
      this.Url = url;
      this.CountryCode = regionInfo.TwoLetterISORegionName;
      this.gameTypes = trimmedGameTypes;
      this.Platform = platform;
      this.ProgrammingLang = programmingLang;
    }
  }
}