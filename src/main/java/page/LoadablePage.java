package page;

import io.appium.java_client.MobileElement;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * The extension for pages that take time to be loaded.
 * Provides {@link #waitLoaded()} method to ensure the page is loaded.
 */
public interface LoadablePage<Page extends AbstractPage> {

    /**
     * Should return elements which have to be visible to assume the page is loaded.
     * If you need a single indicating element override {@link #getLoadedPageElement()} instead.
     * <p/>
     * You could use {@link java.util.Arrays#asList(Object[])} to simplify list creation, e.g.:
     * <code>Arrays.asList(pageIndicator1, pageIndicator2, ...)</code>
     */
    @Nonnull
    default List<MobileElement> getLoadedPageElementList() {
        return Collections.singletonList(getLoadedPageElement());
    }

    /**
     * Should return the single element which have to be visible to assume the page is loaded.
     * If you need more then one indicating element override {@link #getLoadedPageElementList()} instead.
     */
    @Nonnull
    default MobileElement getLoadedPageElement() {
        throw new UnsupportedOperationException("Should be overridden to use");
    }

    /**
     * Waits until all the default elements are visible.
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    default Page waitLoaded() {
        Page page = (Page) this;
        for (MobileElement defaultElement : getLoadedPageElementList()) {
            page.waitUntil(defaultElement);
        }
        return page;
    }
}
