package util;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import java.util.Map;

/**
 * "Soft" assert class. All asserts will fire after assertAll call.
 */
public class SoftAssert extends Assertion {
    private Map<AssertionError, IAssert> m_errors = Maps.newLinkedHashMap();

    private StringBuilder description;

    public SoftAssert() {
    }

    public SoftAssert(String description) {
        if (description != null) {
            this.description = new StringBuilder(description);
        }
    }

    @Override
    public void executeAssert(IAssert a) {
        try {
            a.doAssert();
        } catch(AssertionError ex) {
            onAssertFailure(a, ex);
            m_errors.put(ex, a);
        }
    }

    /**
     * Fire all asserts.
     */
    public void assertAll() {
        if (!m_errors.isEmpty()) {
            StringBuilder sb;
            if (description == null) {
                sb = new StringBuilder("\nFail count: ")
                        .append(m_errors.size())
                        .append("\nThe following asserts failed:\n");
            } else {
                sb = new StringBuilder(description
                                               .append("\nFail count: ")
                                               .append(m_errors.size())
                                               .append("\nThe following asserts failed:\n"));
            }

            boolean first = true;
            int cnt = 1;
            for (Map.Entry<AssertionError, IAssert> ae : m_errors.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    sb.append("\n");
                }
                sb.append("\n[FAIL #").append(cnt).append("]\n").append(ae.getValue().getMessage());
                cnt++;
            }

            sb.append("\n\n");
            throw new AssertionError(sb.toString());
        }
    }

    /**
     * Set {@link SoftAssert} description.
     *
     * @param description description.
     */
    public void setDescription(String description) {
        this.description = new StringBuilder(description);
    }

    /**
     * Append {@link SoftAssert} description.
     *
     * @param description description.
     */
    public void appendDescription(String description){
        this.description.append(description);
    }

    public String getDescription(){
        return description.toString();
    }
}

