import io.github.dmitrymiyuzov.validator.base.ValidatorFabric;

public class Test {
    public static void main(String[] args) {
        ValidatorFabric.beginAssertValidation()
                .assertEqualsSoft("1", 1, 2)
                .assertEqualsSoft("1", 1, 2, "rarar")
                .validate();
    }
}
