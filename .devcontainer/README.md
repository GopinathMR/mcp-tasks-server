# Development Container for MCP Tasks Server

This directory contains configuration files for setting up a consistent development environment using VS Code's Remote - Containers extension.

## Prerequisites

1. [Docker](https://www.docker.com/products/docker-desktop) installed on your machine
2. [Visual Studio Code](https://code.visualstudio.com/) installed on your machine
3. [Remote - Containers extension](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers) installed in VS Code

## Getting Started

1. Open this repository in VS Code
2. When prompted, click "Reopen in Container" or run the "Remote-Containers: Reopen in Container" command from the Command Palette (F1)
3. VS Code will build the container and set up the development environment (this may take a few minutes the first time)
4. Once the container is built, you'll have a fully configured Java development environment with all the necessary extensions and tools

## Features

- Java 21 with Spring Boot development environment
- PostgreSQL database for local development
- Docker-in-Docker support for running containerized applications
- Git integration
- Recommended VS Code extensions pre-installed
- Code formatting and linting tools configured
- Gradle build tool
- ZSH with Oh My Zsh for better terminal experience

## Customizing the Environment

You can customize the development environment by modifying the following files:

- `devcontainer.json`: Configure VS Code settings, extensions, and container settings
- `Dockerfile`: Customize the container image with additional tools or configurations
- `docker-compose.yml`: Add or modify services like databases or other dependencies
- `.zshrc`: Customize the shell environment with aliases and configurations

## Useful Commands

The development container comes with several useful aliases:

### Gradle Commands
- `gw`: Run Gradle wrapper
- `gwb`: Run Gradle build
- `gwt`: Run Gradle tests
- `gwc`: Run Gradle clean
- `gwcb`: Run Gradle clean build
- `gwct`: Run Gradle clean test
- `gwi`: Run Spring Boot application

### Docker Commands
- `d`: Docker
- `dc`: Docker Compose
- `dps`: Docker ps
- `di`: Docker images

### Git Commands
- `gs`: Git status
- `ga`: Git add
- `gc`: Git commit
- `gp`: Git push
- `gl`: Git pull
- `gco`: Git checkout
- `gb`: Git branch

## Troubleshooting

If you encounter any issues with the development container:

1. Try rebuilding the container using the "Remote-Containers: Rebuild Container" command
2. Check Docker logs for any errors
3. Ensure your Docker Desktop has sufficient resources allocated
4. Make sure all required ports are available on your host machine 