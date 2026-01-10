# Git Setup and Push Instructions

## Initial Setup (First Time Only)

If you haven't already initialized the repository:

```bash
# Navigate to your project directory
cd /path/to/your/project

# Initialize Git (if not already done)
git init

# Add the remote repository
git remote add origin https://github.com/Shif789/Eskimi-Backend-Task.git

# Verify the remote
git remote -v
```

## Files to Add to Your Repository

Make sure you have created these files:

- ✅ `Dockerfile`
- ✅ `docker-compose.yml`
- ✅ `.dockerignore`
- ✅ `README.md`
- ✅ `QUICK_START.md`
- ✅ `build-and-run.sh`
- ✅ `.gitignore` (if not already exists)

## Recommended .gitignore

Create a `.gitignore` file with the following content:

```gitignore
# Maven
target/
pom.xml.tag
pom.xml.releaseBackup
pom.xml.versionsBackup
pom.xml.next
release.properties
dependency-reduced-pom.xml
buildNumber.properties
.mvn/timing.properties
.mvn/wrapper/maven-wrapper.jar

# IDE - IntelliJ IDEA
.idea/
*.iml
*.iws
*.ipr
out/

# IDE - Eclipse
.classpath
.project
.settings/
bin/

# IDE - VSCode
.vscode/

# OS
.DS_Store
Thumbs.db

# Logs
*.log
logs/

# Temporary files
*.swp
*.swo
*~
.tmp/
```

## Push to GitHub

### First Push

```bash
# Check the status
git status

# Add all files
git add .

# Commit your changes
git commit -m "Complete Eskimi Backend Assignment with Docker support"

# Push to GitHub (main branch)
git push -u origin main

# OR if your default branch is master:
git push -u origin master
```

### Subsequent Pushes

```bash
# Check what changed
git status

# Add specific files
git add Dockerfile docker-compose.yml README.md
# OR add all changes
git add .

# Commit with a meaningful message
git commit -m "Update Docker configuration and documentation"

# Push to GitHub
git push
```

## Verify Your Push

After pushing, visit:
https://github.com/Shif789/Eskimi-Backend-Task

You should see all your files including:
- Source code (`src/` directory)
- Docker files
- Documentation
- Tests

## Common Git Commands

```bash
# See commit history
git log --oneline

# See changes in files
git diff

# Undo changes to a file (before commit)
git checkout -- filename

# Create and switch to a new branch
git checkout -b feature-branch

# Switch back to main
git checkout main

# Pull latest changes
git pull origin main
```

## Creating a Good Repository Structure

Your final repository should look like this:

```
Eskimi-Backend-Task/
├── .github/                    # (Optional) GitHub workflows
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── .gitignore
├── .dockerignore
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
├── QUICK_START.md
├── build-and-run.sh
└── LICENSE                     # (Optional)
```

## Final Checklist Before Submission

- [ ] All code is committed and pushed
- [ ] README.md has clear build and run instructions
- [ ] Dockerfile builds successfully
- [ ] All tests pass
- [ ] Docker image runs successfully
- [ ] API endpoints are documented
- [ ] Repository is public or shared with evaluators
- [ ] No sensitive data (API keys, passwords) in repository

## Making Repository Public

1. Go to https://github.com/Shif789/Eskimi-Backend-Task
2. Click "Settings"
3. Scroll down to "Danger Zone"
4. Click "Change visibility" → "Make public"

## Adding a Nice README Badge (Optional)

Add this to the top of your README.md for a professional touch:

```markdown
# Eskimi Backend Assignment

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![Tests](https://img.shields.io/badge/Tests-Passing-success)
```

## Need Help?

If you encounter any issues:

1. **Check Git status**: `git status`
2. **Check remote**: `git remote -v`
3. **Pull latest changes**: `git pull origin main`
4. **Force push (use carefully)**: `git push -f origin main`

---

**Repository URL:** https://github.com/Shif789/Eskimi-Backend-Task
