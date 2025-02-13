# eshop

## <ins> Reflection 1 </ins>

### Clean Code Principles
- **Meaningful Names**: Class and method names clearly reflect their purpose.
- **Functions**: Controller methods are focused on single, well-defined tasks (e.g., create, edit).
- **Comments**: Kept minimal by using self-explanatory names and short docstrings where necessary.
- **Data Structures**: Product objects store fields relevant to product data, with clear getters/setters.
- **Error Handling**: Handled via null checks (redirecting if product is missing).

### Secure Coding Practices
- **ID Generation**: Ensuring UUID is assigned if none is present helps prevent ID collisions.
- **Validation (Future Improvement)**: Input fields could use bean validation annotations for stricter checks.
- **Authorization (Future Improvement)**: Restrict certain endpoints to specific user roles if relevant.
- **Output Data Encoding (Future Improvement)**: To mitigate potential XSS attacks, especially with dynamic fields.

### Possible Improvements
- Add more exception handling for invalid product data or edge cases.
- Insert test coverage (unit/functional) to ensure code changes do not break existing features.

## <ins> Reflection 2 </ins>

After creating unit tests, I feel more confident that the application behaves as intended. Tests serve as a safety net: if something breaks, we can detect it quickly. There is no strict rule on how many unit tests a class should have—it depends on the complexity of the class and the number of behaviors or edge cases we want to verify. Code coverage can help gauge whether we have tested most of our code paths. However, 100% coverage does not necessarily guarantee the absence of bugs, since coverage only measures whether a line was executed, not whether it was tested with all relevant inputs or scenarios.

Regarding the new functional test suite for verifying the number of items in the product list, here are some observations about cleanliness and how it might affect code quality:

1. **Potential Duplication**  
   - If we have multiple functional test classes that each replicate setup procedures (e.g., creating a driver instance, navigating to the same pages), the code becomes more verbose and harder to maintain.  
   - Repetitive setup logic could be centralized in a base test class or utility methods.

2. **Hard-Coded Values and Shared State**  
   - If we repeatedly use hard-coded product IDs or names across different test methods, it can reduce clarity and make maintenance harder.  
   - Extracting these to constants or using methods that generate test-friendly data can improve readability.

3. **Single Responsibility in Tests**  
   - Each test should focus on validating one aspect or scenario. Overloading a single test method with multiple verification steps can make it harder to debug when something fails.  
   - Ensure each test method name clearly describes the expected outcome.

4. **Readability and Naming**  
   - Descriptive test names (e.g., “verifyProductCountMatchesExpected_whenMultipleProductsCreated”) help readers quickly identify a test’s purpose.  
   - Consistent naming conventions across all test files foster readability and maintainability.

5. **Test Data Management**  
   - Beyond verifying the product count, consider whether you can reuse code (like product creation steps) while still adhering to “Arrange, Act, Assert” patterns cleanly.  
   - Excessive copy-paste of the same product creation logic might be replaced with a helper function or a setup method.

**Suggestions for Improvement:**
- **Use a Base Functional Test Class:** Centralize any shared logic (e.g., driver setup, teardown, base URL configuration) so each test suite is lean and focused on its scenario.
- **Maintain Meaningful Test Names:** Each test name should highlight the specific condition being tested and the expected outcome.  
- **Keep an Eye on Coverage—but Don’t Rely on It Alone:** Use code coverage reports to identify untested paths or classes, while still assessing tests for completeness and real-world scenarios.