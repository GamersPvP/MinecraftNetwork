# MinecraftNetwork

This is an experimental project of my own Minecraft server network, primarily its backend, including:

- Player data management
- Real-time inter-server messaging
- BungeeCord proxy synchronization
- Real-time server and player state management
- Minigame system
- Internalization
- GUI framework
- Party system
- Configuration framework
- Punishment system
- Task framework
- Shop module
- Command framework

The whole thing is put together into one monolithic plugin.

# Technologies used:

- Google Guice
- Lombok
- Redis
- Ebean ORM
- RabbitMQ
- Mockito
- H2 Database
- SLF4J

# Functionality

### Players

- Configure personal preferences, choose whether the chat is shown, party requests are enabled and so forth.
- Access any real-time data about any online servers (status, player count) or players (current server).
- Send private messages to other online players, regardless of the receiver proxy.
- Ignore other players, i.e prevent them from sending private messages. 
- Purchase Ranks to enhance own in-game experience.
- Choose the preferred server interface language.
- Make in-server purchases for the game currency. 
- Create parties and play with the friends.

### BungeeCord

- Automatic server registration at BungeeCord eliminating the need to specify servers manually in the Bungee config.
- Each proxy is capable of displaying global network online, not just its own.
- Cross-proxy party system.
- Lobby load balancing.   

### Server data

- Real-time storing, updating and fetching data of the state of all servers in the network.
- Fast instant joining (choosing the most suitable game server for the joining).
- Creating user interfaces for server selection (join signs, GUIs and so on).
- Bulk server data fetching.

### Minigames

- Kit system with configurable kit acquisition options, including purchasing or rank-based permissions. 
- Multiple options of the winner calculation, based on places, time of the elimination and more.
- Multiple elimination modes, including lives, respawning or immediate elimination.
- Support for the spectators, in-game joining.
- Dozens of configurable game options.
- Multiple game arenas, map voting.
- Team and solo game modes.
- Game setup mode. 

### Staff

- Ban, mute, kick or warn players permanently or for the certain time, specifying punishment reasons.
- Broadcast important messages throughout the BungeeCord proxies.
