# Paper Prefix

A simple and flexible prefix plugin designed for your PaperMC server.

Give your players the ability to stand out and personalize their identity! **Paper Prefix** is a lightweight and user-friendly plugin that allows players to set their own custom prefixes, which will appear in chat, the player list, and above their nametags.

## Features

*   **Easy for Players**: Players can set their own prefix and color with a simple command: `/prefix set <text> [color]`.
*   **Rich Color Support**: Supports all standard Minecraft colors, plus unique Bedrock colors like `netherite`, `emerald`, `amethyst`, and `minecoin_gold`.
*   **Seamless Integration**: Prefixes are automatically displayed in three key places:
    *   In-game chat
    *   The player list (tab menu)
    *   Above player nametags
*   **Persistent**: Prefixes are saved and automatically re-applied when a player joins the server.
*   **Lightweight**: Designed to be efficient and not impact your server's performance.

## Commands

The alias `/status` can also be used for all commands.

| Command | Description | Permission |
| --- | --- | --- |
| `/prefix set <text> [color]` | Sets your personal prefix. | `paperprefix.set` |
| `/prefix remove` | Removes your current prefix. | `paperprefix.set` |
| `/prefix join <username>` | Copies the prefix from another player. | `paperprefix.join` |

## Permissions

| Permission | Description | Default |
| --- | --- | --- |
| `paperprefix.set` | Allows a player to set and remove their own prefix. | `true` |
| `paperprefix.join` | Allows the player to copy another player's prefix with `/prefix join`. | `op` |

## PlaceholderAPI Support

This plugin provides a PlaceholderAPI placeholder to show a player's prefix.

`%paperprefix_prefix%`

## Building

To compile the plugin from source, you will need:
*   Java Development Kit (JDK) 17 or newer
*   Apache Maven

Clone the repository and run the following command in the root directory:
```bash
mvn clean package
```
The compiled `.jar` file will be located in the `target` directory.

## Installation

*   Make sure you are running a PaperMC or PaperMC-compatible server.
*   Download the latest `Paper-Prefix.jar` file from the [releases page](https://github.com/b1o-eu/Paper-Prefix/releases).
*   Drop the file into your server's `/plugins` folder.
*   Restart your server.

That's it! No complicated configuration is needed. Your players can start setting their prefixes right away.

## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---
<p align="center">
  <em>Thank you for using Paper Prefix!</em>
</p>