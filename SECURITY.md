# Security Policy

## Supported Versions

We actively support the following versions of BloodMate with security updates:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |
| < 1.0   | :x:                |

## Security Considerations

BloodMate handles sensitive medical data and blood bank operations. Security is paramount for protecting:

- Patient personal health information (PHI)
- Donor sensitive data
- Blood inventory records
- Emergency response systems
- Administrative access controls

## Reporting a Vulnerability

**DO NOT** report security vulnerabilities through public GitHub issues.

Instead, please report them responsibly:

### Preferred Method
Send an email to: **security@bloodmate.org**

Include the following information:
- Type of vulnerability
- Location of vulnerable code
- Step-by-step reproduction instructions
- Potential impact assessment
- Suggested remediation (if available)

### Response Timeline
- **Initial Response**: Within 24 hours
- **Vulnerability Assessment**: Within 5 business days
- **Fix Development**: Based on severity (Critical: 1-3 days, High: 1 week, Medium: 2 weeks)
- **Public Disclosure**: After fix deployment (coordinated disclosure)

## Security Best Practices

### For Users
- Use strong passwords for database connections
- Enable SSL/TLS for database connections
- Regularly update Java and dependencies
- Implement network security measures
- Follow healthcare data protection regulations (HIPAA, GDPR)

### For Developers
- Never commit sensitive data (passwords, API keys)
- Use parameterized queries to prevent SQL injection
- Validate all user inputs
- Implement proper access controls
- Regular security audits and dependency checks

## Vulnerability Severity Levels

- **Critical**: Immediate threat to patient data or system integrity
- **High**: Significant security risk requiring prompt attention
- **Medium**: Security issue with moderate impact
- **Low**: Minor security concern

## Security Acknowledgments

We appreciate responsible security researchers who help improve BloodMate's security. Acknowledged contributors will be listed here (with permission).

## Contact

For security-related questions: security@bloodmate.org
For general questions: support@bloodmate.org