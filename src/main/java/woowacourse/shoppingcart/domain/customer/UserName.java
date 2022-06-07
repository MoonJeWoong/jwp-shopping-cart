package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.invalid.InvalidUserNameException;

public class UserName {

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 20;
    private static final Pattern PATTERN = Pattern.compile("^[a-z1-9_]*$");

    private final String value;

    public UserName(final String userName) {
        validateLength(userName);
        validatePattern(userName);
        this.value = userName;
    }

    private void validateLength(final String userName) {
        if (userName.length() < MIN_LENGTH || userName.length() > MAX_LENGTH) {
            throw new InvalidUserNameException();
        }
    }

    private void validatePattern(final String userName) {
        if (!PATTERN.matcher(userName).matches()) {
            throw new InvalidUserNameException();
        }
    }

    public String getValue() {
        return value;
    }
}