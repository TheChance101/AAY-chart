# Contributing to AAY-Chart

## Branching Strategy
- All feature development should happen on `feature/` branches.
- All Pull Requests should target the `develop` branch.
- The `main` branch is reserved for stable releases.

## Pull Requests
- Every Pull Request must be reviewed by at least one maintainer (e.g., @The_chance).
- CI must pass before merging.
- Ensure your code follows the existing style.

## CI/CD Pipeline
We use GitHub Actions for our CI/CD:
- **CI**: Runs on every PR to `develop` or `main`. Builds the project and runs checks.
- **CD**: Runs when a new release is published. Publishes the library to Maven Central.

### Setting up Secrets for CD
To enable the CD pipeline, you must configure the following secrets in your GitHub repository:
- `MAVEN_CENTRAL_USERNAME`: Your Sonatype Central Portal username.
- `MAVEN_CENTRAL_PASSWORD`: Your Sonatype Central Portal password.
- `SIGNING_KEY_ID`: Your GPG key ID (last 8 characters).
- `SIGNING_PASSWORD`: Your GPG key password.
- `SIGNING_SECRET_KEY`: Your GPG private key (ASCII-armored).

## Local Development
- Use `local.properties` for local overrides of publishing credentials.
- Do NOT commit sensitive information to `gradle.properties`.
