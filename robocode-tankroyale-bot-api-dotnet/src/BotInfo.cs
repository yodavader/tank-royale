using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.Versioning;
using System.Text.RegularExpressions;
using System.Threading;
using Microsoft.Extensions.Configuration;
using Robocode.TankRoyale.BotApi.Util;

namespace Robocode.TankRoyale.BotApi
{
  /// <summary>
  /// Bot info contains the properties of a bot.
  /// </summary>
  public sealed class BotInfo
  {
    private string name;
    private string version;
    private IEnumerable<string> authors;
    private IEnumerable<string> contryCodes;
    private IEnumerable<string> gameTypes;
    private string platform;

    /// <summary>
    /// The name, e.g., "MyBot". This field must always be provided with the bot info.
    /// </summary>
    /// <value>The name of the bot.</value>
    public string Name
    {
      get => name;
      private set
      {
        if (string.IsNullOrWhiteSpace(value))
          throw new ArgumentException("Name cannot be null, empty or blank");
        name = value;
      }
    }

    /// <summary>
    /// The version, e.g., "1.0". This field must always be provided with the bot info.
    /// </summary>
    /// <value>The version of the bot.</value>
    public string Version
    {
      get => version;
      private set
      {
        if (string.IsNullOrWhiteSpace(value))
          throw new NullReferenceException("Version cannot be null, empty or blank");
        version = value;
      }
    }

    /// <summary>
    /// List of author(s) of the bot, e.g., "John Doe (johndoe@somewhere.io)".
    /// At least one author must be provided.
    /// </summary>
    /// <value>The author(s) of the bot.</value>
    public IEnumerable<string> Authors
    {
      get => authors;
      private set
      {
        if (value.IsNullOrEmptyOrContainsBlanks())
          throw new ArgumentException("Authors cannot be null or empty or contain blanks");
        authors = value.ToListWithNoBlanks();
      }
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
    /// The country code(s) defined by ISO 3166-1 alpha-2, e.g. "us":
    /// https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2.
    /// If no country code is provided, the locale of the system is being used instead.
    /// </summary>
    /// <value>The country code(s) for the bot.</value>
    public IEnumerable<string> CountryCodes
    {
      get => contryCodes;
      private set
      {
        contryCodes = value.ToListWithNoBlanks();

        foreach (string countryCode in contryCodes)
        {
          if (!string.IsNullOrWhiteSpace(countryCode))
          {
            try
            {
              // Check country code by passing it as input parameter to RegionInfo
              new RegionInfo(countryCode.Trim());
            }
            catch (ArgumentException)
            {
              throw new ArgumentException($"Country Code is not valid: '{countryCode}'");
            }
          }
        }

        if (!CountryCodes.Any())
        {
          var list = new List<string>();
          // Get local country code
          list.Add(Thread.CurrentThread.CurrentCulture.Name);
          contryCodes = list;
        }
      }
    }

    /// <summary>
    /// The game type(s) accepted by the bot, e.g., "classic", "melee", "1v1". This field must always be
    /// provided with the bot info. The game types define which game types the bot can participate
    /// in. See <see cref="GameType"/> for using predefined game type.
    /// </summary>
    /// <value>The game type(s) that this bot can handle.</value>
    public IEnumerable<string> GameTypes
    {
      get => gameTypes;
      private set
      {
        if (value.IsNullOrEmptyOrContainsBlanks())
          throw new ArgumentException("Game types cannot be null or empty or contain blanks");
        gameTypes = value.ToListWithNoBlanks().Distinct().ToHashSet();
      }
    }

    /// <summary>
    /// The platform used for running the bot, e.g., ".Net 5.0".
    /// This field is optional.
    /// </summary>
    /// <value>The platform used for running the bot.</value>
    public string Platform
    {
      get => platform;
      private set
      {
        if (string.IsNullOrWhiteSpace(value))
          value = Assembly.GetEntryAssembly()?.GetCustomAttribute<TargetFrameworkAttribute>()?.FrameworkName;
        platform = value;
      }
    }

    /// <summary>
    /// The programming language used for developing the bot, e.g., "C# 8.0" or "F#".
    /// This field is optional.
    /// </summary>
    /// <value>The programming language used for developing the bot.</value>
    public string ProgrammingLang { get; private set; }

    /// <summary>
    /// Initializes a new instance of the BotInfo class.
    /// </summary>
    /// <param name="name">The name of the bot (required).</param>
    /// <param name="version">The version of the bot (required).</param>
    /// <param name="authors">The author(s) of the bot (required).</param>
    /// <param name="description">A short description of the bot (optional).</param>
    /// <param name="url">The URL to a web page for the bot (optional).</param>
    /// <param name="countryCodes">The country code(s) for the bot (optional).</param>
    /// <param name="gameTypes">The game type(s) that this bot can handle (required).</param>
    /// <param name="platform">The platform used for running the bot (optional).</param>
    /// <param name="programmingLang">The programming language used for developing the bot (optional).</param>
    public BotInfo(
      string name,
      string version,
      IEnumerable<string> authors,
      string description,
      string url,
      IEnumerable<string> countryCodes,
      IEnumerable<string> gameTypes,
      string platform,
      string programmingLang)
    {
      Name = name;
      Version = version;
      Authors = authors;
      Description = description;
      Url = url;
      CountryCodes = countryCodes;
      GameTypes = gameTypes;
      Platform = platform;
      ProgrammingLang = programmingLang;
    }

    /// <summary>
    /// Reads the bot info from a JSON file in the specified base path.
    /// </summary>
    /// <example>
    /// Using a appsettings.json file:
    /// <code>
    /// {
    ///   "Bot": {
    ///     "Name": "MyBot",
    ///     "Version": "1.0",
    ///     "Authors": "John Doe",
    ///     "Description": "A short description",
    ///     "Url": "http://somewhere.net/MyBot",
    ///     "CountryCodes": "us",
    ///     "GameTypes": "classic, melee, 1v1",
    ///     "Platform": ".Net 5.0",
    ///     "ProgrammingLang": "C# 8.0"
    ///   }
    /// }
    /// </code>
    /// </example>
    /// <param name="filePath">Is the file path, e.g. "bot-settings.json</param>
    /// <param name="basePath">Is the base path, e.g. Directory.GetCurrentDirectory().
    /// If null, the current directory will automatically be used as base path</param>
    /// <returns> A BotInfo instance containing the bot properties read from the configuration.</returns>
    public static BotInfo FromJsonFile(string filePath, string basePath)
    {
      if (basePath == null)
      {
        basePath = Directory.GetCurrentDirectory();
      }
      var configBuilder = new ConfigurationBuilder().SetBasePath(basePath).AddJsonFile(filePath);
      var config = configBuilder.Build();

      return BotInfo.FromConfiguration(config);
    }

    /// <summary>
    /// Reads the bot info from a JSON file from the current directory.
    /// </summary>
    /// <example>
    /// Using a appsettings.json file:
    /// <code>
    /// {
    ///   "Bot": {
    ///     "Name": "MyBot",
    ///     "Version": "1.0",
    ///     "Authors": "John Doe",
    ///     "Description": "A short description",
    ///     "Url": "http://somewhere.net/MyBot",
    ///     "CountryCodes": "us",
    ///     "GameTypes": "classic, melee, 1v1",
    ///     "Platform: ".Net 5.0",
    ///     "ProgrammingLang": "C# 8.0"
    ///   }
    /// }
    /// </code>
    /// </example>
    /// <param name="filePath">Is the file path, e.g. "bot-settings.json</param>
    /// <param name="basePath">Is the base path, e.g. Directory.GetCurrentDirectory().
    /// If null, the current directory will automatically be used as base path</param>
    /// <returns> A BotInfo instance containing the bot properties read from the configuration.</returns>
    public static BotInfo FromJsonFile(string filePath)
    {
      return FromJsonFile(filePath, null);
    }

    /// <summary>
    /// Reads the bot info from a configuration.
    /// </summary>
    /// <example>
    /// Using a appsettings.json file:
    /// <code>
    /// {
    ///   "Bot": {
    ///     "Name": "MyBot",
    ///     "Version": "1.0",
    ///     "Authors": "John Doe",
    ///     "Description": "A short description",
    ///     "Url": "http://somewhere.net/MyBot",
    ///     "CountryCodes": "us",
    ///     "GameTypes": "classic, melee, 1v1",
    ///     "Platform": ".Net 5.0",
    ///     "ProgrammingLang": "C# 8.0"
    ///   }
    /// }
    /// </code>
    /// </example>
    /// <param name="configuration">Is the configuration</param>
    /// <returns> A BotInfo instance containing the bot properties read from the configuration.</returns>
    public static BotInfo FromConfiguration(IConfiguration configuration)
    {
      var name = configuration["Bot:Name"];
      if (string.IsNullOrWhiteSpace(name))
      {
        throw new ArgumentException("Bot:Name is missing");
      }
      Console.WriteLine("Bot name: " + name);

      var version = configuration["Bot:Version"];
      if (string.IsNullOrWhiteSpace(version))
      {
        throw new ArgumentException("Bot:Version is missing");
      }
      Console.WriteLine("Bot version: " + version);
      var authors = configuration["Bot:Authors"];
      if (string.IsNullOrWhiteSpace(authors))
      {
        throw new ArgumentException("Bot:Authors is missing");
      }
      Console.WriteLine("Bot authors: " + authors);
      var countryCodes = configuration["Bot:CountryCodes"];
      if (string.IsNullOrWhiteSpace(countryCodes))
      {
        countryCodes = "";
      }
      Console.WriteLine("Bot country codes: " + countryCodes);
      var gameTypes = configuration["Bot:GameTypes"];
      if (string.IsNullOrWhiteSpace(gameTypes))
      {
        throw new ArgumentException("Bot:GameTypes is missing");
      }
      Console.WriteLine("Bot gameTypes: " + gameTypes);
      return new BotInfo(
        name,
        version,
        Regex.Split(authors, @"\s*,\s*"),
        configuration["Bot:Description"],
        configuration["Bot:Url"],
        Regex.Split(countryCodes, @"\s*,\s*"),
        Regex.Split(gameTypes, @"\s*,\s*"),
        configuration["Bot:Platform"],
        configuration["Bot:ProgrammingLang"]
      );
    }
  }
}