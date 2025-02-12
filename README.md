# eshop

# Reflection on Clean Code and Secure Coding

## Clean Code Principles
- **Meaningful Names**: Class and method names clearly reflect their purpose.
- **Functions**: Controller methods are focused on single, well-defined tasks (e.g., create, edit).
- **Comments**: Kept minimal by using self-explanatory names and short docstrings where necessary.
- **Data Structures**: Product objects store fields relevant to product data, with clear getters/setters.
- **Error Handling**: Handled via null checks (redirecting if product is missing).

## Secure Coding Practices
- **ID Generation**: Ensuring UUID is assigned if none is present helps prevent ID collisions.
- **Validation (Future Improvement)**: Input fields could use bean validation annotations for stricter checks.
- **Authorization (Future Improvement)**: Restrict certain endpoints to specific user roles if relevant.
- **Output Data Encoding (Future Improvement)**: To mitigate potential XSS attacks, especially with dynamic fields.

## Possible Improvements
- Add more exception handling for invalid product data or edge cases.
- Insert test coverage (unit/functional) to ensure code changes do not break existing features.
