# Contributing to BloodMate

Thank you for your interest in contributing to BloodMate! This document provides guidelines and instructions for contributing to this revolutionary blood bank management system.

## 🩸 Our Mission

BloodMate aims to revolutionize blood bank management through innovative technology, improving efficiency and saving lives. We welcome contributions that align with this mission.

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Testing Guidelines](#testing-guidelines)
- [Documentation](#documentation)
- [Issue Reporting](#issue-reporting)
- [Pull Request Process](#pull-request-process)
- [Community](#community)

## 📜 Code of Conduct

This project adheres to our [Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code. Please report unacceptable behavior to the project maintainers.

## 🚀 Getting Started

### Prerequisites

- Java 17+ (OpenJDK recommended)
- Maven 3.8+
- MySQL 8.0+
- Git
- IDE with Java support (IntelliJ IDEA, Eclipse, VS Code)

### Setting Up Development Environment

1. **Fork the repository**
   ```bash
   git clone https://github.com/yourusername/BloodMate-Web-Test.git
   cd BloodMate-Web-Test
   ```

2. **Set up database**
   ```sql
   CREATE DATABASE bloodmate_dev;
   ```

3. **Configure application**
   ```bash
   cp desktop/src/main/resources/database.properties.example desktop/src/main/resources/database.properties
   # Edit database.properties with your local settings
   ```

4. **Build and run**
   ```bash
   cd desktop
   mvn clean install
   mvn javafx:run
   ```

## 🔄 Development Workflow

### Branching Strategy

We use Git Flow branching model:

- `main`: Production-ready code
- `develop`: Integration branch for features
- `feature/*`: New features
- `bugfix/*`: Bug fixes
- `hotfix/*`: Critical fixes for production

### Creating Feature Branches

```bash
git checkout develop
git pull origin develop
git checkout -b feature/your-feature-name
```

### Making Changes

1. Make your changes in small, logical commits
2. Write clear commit messages
3. Test your changes thoroughly
4. Update documentation as needed

### Commit Message Format

```
type(scope): brief description

Detailed explanation of the change, including:
- What was changed
- Why it was changed
- Any breaking changes

Closes #issue-number
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes
- `refactor`: Code refactoring
- `test`: Adding or modifying tests
- `chore`: Maintenance tasks

## 📝 Coding Standards

### Java Style Guidelines

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Write JavaDoc for public methods
- Maximum line length: 120 characters
- Use 4 spaces for indentation

### JavaFX Specific Guidelines

- FXML files should have proper indentation
- CSS classes should follow kebab-case naming
- Controller classes should end with "Controller"
- Use proper ScrollPane implementation for new views

### Database Guidelines

- Use descriptive table and column names
- Include proper foreign key constraints
- Add appropriate indexes
- Write migration scripts for schema changes

### Example Code Style

```java
/**
 * Manages donor registration and profile updates.
 */
public class DonorService {
    
    private final DonorDao donorDao;
    private static final Logger logger = LoggerFactory.getLogger(DonorService.class);
    
    /**
     * Registers a new donor in the system.
     *
     * @param donor the donor to register
     * @return the registered donor with generated ID
     * @throws DonorRegistrationException if registration fails
     */
    public Donor registerDonor(Donor donor) throws DonorRegistrationException {
        validateDonor(donor);
        
        try {
            return donorDao.save(donor);
        } catch (SQLException e) {
            logger.error("Failed to register donor: {}", donor.getEmail(), e);
            throw new DonorRegistrationException("Registration failed", e);
        }
    }
}
```

## 🧪 Testing Guidelines

### Unit Tests

- Write unit tests for all service classes
- Use JUnit 5 and Mockito
- Aim for 80%+ code coverage
- Test both success and failure scenarios

### Integration Tests

- Test database operations
- Test UI components with TestFX
- Verify system integrations

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=DonorServiceTest

# Run tests with coverage
mvn test jacoco:report
```

## 📚 Documentation

### Code Documentation

- Write JavaDoc for all public APIs
- Include usage examples
- Document complex algorithms
- Update README for new features

### User Documentation

- Update user guides for new features
- Include screenshots for UI changes
- Maintain installation instructions
- Document configuration options

## 🐛 Issue Reporting

### Before Reporting

1. Search existing issues
2. Check if issue exists in latest version
3. Reproduce the issue consistently

### Bug Report Template

```markdown
**Bug Description**
A clear description of the bug.

**Steps to Reproduce**
1. Go to '...'
2. Click on '...'
3. See error

**Expected Behavior**
What you expected to happen.

**Screenshots**
Add screenshots if applicable.

**Environment**
- OS: [e.g., Windows 10]
- Java Version: [e.g., 17.0.1]
- BloodMate Version: [e.g., 1.0.0]
```

### Feature Request Template

```markdown
**Feature Description**
A clear description of the feature.

**Problem Statement**
What problem does this solve?

**Proposed Solution**
How should this feature work?

**Additional Context**
Any other context or screenshots.
```

## 🔀 Pull Request Process

### Before Submitting

1. Ensure all tests pass
2. Update documentation
3. Check code coverage
4. Rebase on latest develop branch

### PR Description Template

```markdown
## Description
Brief description of changes.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing completed

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] No breaking changes (or documented)
```

### Review Process

1. Automated checks must pass
2. At least one maintainer review required
3. All review comments addressed
4. Squash commits before merge

## 🏷️ Labels and Milestones

### Issue Labels

- `bug`: Something isn't working
- `enhancement`: New feature or request
- `good first issue`: Good for newcomers
- `help wanted`: Extra attention needed
- `documentation`: Documentation improvements
- `question`: Further information needed

### Priority Labels

- `priority-high`: Critical issues
- `priority-medium`: Important improvements
- `priority-low`: Nice to have features

## 🌟 Recognition

Contributors will be recognized in:
- CHANGELOG.md
- Project README
- Annual contributor report

## 📞 Getting Help

### Community Channels

- **GitHub Issues**: Technical questions and bug reports
- **Discussions**: General questions and ideas
- **Email**: [bloodmate-dev@example.com]

### Maintainer Contact

- **Lead Developer**: [maintainer@example.com]
- **Technical Lead**: [tech-lead@example.com]

## 📊 Development Metrics

We track:
- Code coverage
- Test execution time
- Build success rate
- Issue resolution time

## 🔐 Security

For security vulnerabilities, please refer to our [Security Policy](SECURITY.md).

## 📄 License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

Thank you for contributing to BloodMate! Together, we're building technology that saves lives. 🩸❤️