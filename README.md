# Advance Programming Course
This repository contains the modules, tutorials, and exercises for the Advance Programming Course.

## Table of Contents
1. [Module 1](#module-1)

## Module 1 - Coding Standard <a id="module-1"></a>

## <ins> Reflection 1 </ins>

### Clean Code Principles
- **Meaningful Names**: Class and method names clearly reflect their purpose.
- **Functions**: Controller methods are focused on single, well-defined tasks (for example, creating or editing a product).
- **Comments**: Kept minimal by using self-explanatory names and concise docstrings where necessary.
- **Data Structures**: Product objects store only the relevant fields for a product, using clear getters/setters.
- **Error Handling**: With bean validation enabled, we handle null or invalid inputs by returning early and directing users back to the form.

#### Example Code Snippet – Creating a Product
Below is an example of a controller method showing how we handle a new product creation request. We validate user input via bean validation (e.g., checking that the product name is not blank and quantity is ≥ 1). If validation fails, we return to the creation form; otherwise, we create the product and redirect to the product list:

```java
@PostMapping("/create")
public String createProductPost(@Valid @ModelAttribute Product product,
                                BindingResult result,
                                Model model) {
    if (result.hasErrors()) {
        // Return back to the create form if validation fails
        return "createProduct";
    }
    service.create(product);
    return "redirect:list";
}
```

And here is a simplified HTML form (using Thymeleaf) that users fill out to create the product. Notice how the form fields map directly to the `Product` class fields:

```html
<form th:action="@{/product/create}" th:object="${product}" method="post">
    <div class="form-group">
        <label for="nameInput">Name</label>
        <input th:field="*{productName}" type="text" class="form-control mb-4 col-4" id="nameInput">
    </div>
    <div class="form-group">
        <label for="quantityInput">Quantity</label>
        <input th:field="*{productQuantity}" type="text" class="form-control mb-4 col-4" id="quantityInput">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
```

By combining clear naming, concise validation rules, and straightforward form rendering, we keep our code both readable and reliable.

### Secure Coding Practices
- **ID Generation**: We assign a UUID if none is set to prevent ID collisions.
- **Validation (Future Improvement)**: Bean validation can be extended (e.g., more specific constraints).
- **Authorization (Future)**: Restrict certain endpoints to authorized roles.
- **Output Data Encoding (Future)**: Encode data to mitigate XSS, especially in dynamically rendered fields.

### Possible Improvements
- Add more comprehensive exception handling for invalid edge cases.
- Increase test coverage (unit and functional tests) to ensure that changes do not break existing features.

---

## <ins> Reflection 2 </ins>

After creating unit tests, I feel more confident about the application’s reliability. Tests act as an early warning system—if something changes or breaks, the tests can catch it quickly. We typically decide on the number of tests by looking at the class’s complexity and the range of scenarios to be verified. Code coverage tools can reveal untested lines or paths, but 100% coverage alone does not guarantee bug-free code.

With our new functional test suite, aimed at checking product creation and count on the list page, here are some key considerations for code cleanliness:

1. **Potential Duplication**
   - Repeated setup or teardown logic scattered across test classes can reduce clarity.
   - Centralizing common steps in a base test class or utility methods aids maintainability.

2. **Hard-Coded Values and Shared State**
   - Overusing fixed values (e.g., product IDs, names) can hamper readability.
   - Generating random or parameterized test data often leads to clearer, more robust tests.

3. **Single Responsibility in Tests**
   - Tests documenting multiple scenarios at once can be confusing.
   - Each test should validate one primary condition. This helps speed up debugging when a test fails.

4. **Readability and Naming**
   - Clear, descriptive test names (e.g., `shouldCreateProduct_whenValidData`) boost understandability
   - Consistent naming conventions reinforce a coherent testing strategy across the project.

5. **Test Data Management**
   - Utility methods for creating or resetting sample data can reduce code clutter while still respecting “Arrange, Act, Assert.”
   - Overly repeated creation steps can be refactored into either `@BeforeEach` or dedicated helper methods.

**Suggestions for Improvement**:
- **Use a Base Functional Test Class**: Move common testing logic (driver setup, teardown, or repeated form fill steps) into a parent class.
- **Maintain Meaningful Test Names**: Let the test name explain the condition verified and the expected outcome.
- **Keep an Eye on Coverage—but Don’t Rely on It Alone**: Use code coverage to find untested areas but remember that executed code isn’t necessarily fully tested.