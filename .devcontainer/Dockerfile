FROM mcr.microsoft.com/devcontainers/java:21

# Install additional packages
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends \
    curl \
    wget \
    jq \
    unzip \
    htop \
    net-tools \
    lsof \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Install Docker CLI
RUN curl -fsSL https://get.docker.com | sh

# Install Gradle (latest)
RUN su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle"

# Install additional SDKs or tools if needed
# For example, to install a specific version of Gradle:
# RUN su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle 8.5"

# Set up shell configuration for better developer experience
RUN echo "alias ll='ls -alF'" >> /home/vscode/.bashrc \
    && echo "alias la='ls -A'" >> /home/vscode/.bashrc \
    && echo "alias l='ls -CF'" >> /home/vscode/.bashrc

# Set up Git configuration
RUN su vscode -c "git config --global core.editor 'code --wait'" \
    && su vscode -c "git config --global init.defaultBranch main"

# Set up ZSH with Oh My Zsh for better terminal experience
RUN sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)" "" --unattended \
    && chsh -s /bin/zsh vscode

# Copy ZSH configuration
COPY .zshrc /home/vscode/.zshrc
RUN chown vscode:vscode /home/vscode/.zshrc 