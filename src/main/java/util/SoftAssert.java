package util;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import java.util.Map;

public class SoftAssert extends Assertion {

    private Map<AssertionError, IAssert> errorsList = Maps.newLinkedHashMap();

    private StringBuilder description;

    public SoftAssert() {
    }

    public SoftAssert(String description) {
        if (description != null) {
            this.description = new StringBuilder(description);
        }
    }

    @Override
    public void executeAssert(IAssert assertCommand) {
        try {
            assertCommand.doAssert();
        } catch (AssertionError ex) {
            onAssertFailure(assertCommand, ex);
            errorsList.put(ex, assertCommand);
        }
    }

    public void assertAll() {
        if (!errorsList.isEmpty()) {
            StringBuilder stringBuilder;
            if (description == null) {
                stringBuilder = new StringBuilder("\nFail count: ")
                        .append(errorsList.size())
                        .append("\nThe following asserts failed:\n");
            } else {
                stringBuilder = new StringBuilder(
                        description
                                .append("\nFail count: ")
                                .append(errorsList.size())
                                .append("\nThe following asserts failed:\n"));
            }
            boolean first = true;
            int cnt = 1;
            for (Map.Entry<AssertionError, IAssert> ae : errorsList.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    stringBuilder.append("\n");
                }
                stringBuilder.append("\n[FAIL #").append(cnt).append("]\n").append(ae.getValue().getMessage());
                cnt++;
            }
            stringBuilder.append("\n\n");
            throw new AssertionError(stringBuilder.toString());
        }

    }

    public void setDescription(String description) {
        this.description = new StringBuilder(description);
    }

    public void appendDescription(String description) {
        this.description.append(description);
    }

    public String getDescription() {
        return description.toString();
    }
}
