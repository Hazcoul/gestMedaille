package bf.gov.gcob.medaille.utils.web;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

public final class PaginationUtil {

    private static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";

    private static LinkHeaderUtil linkHeaderUtil = new LinkHeaderUtil();

    //======================
    private static final String HEADER_LINK_FORMAT = "<{0}>; rel=\"{1}\"";
    private static final String HEADER_X_PAGE_NUMBER = "X-Page-Number";
    private static final String HEADER_X_PAGE_SIZE = "X-Page-Size";
    private static final String HEADER_X_TOTAL_PAGE = "X-Total-Pages";

    private PaginationUtil() {
    }

    /**
     * Generate pagination headers for a Spring Data
     * {@link org.springframework.data.domain.Page} object.
     *
     * @param uriBuilder The URI builder.
     * @param page The page.
     * @param <T> The type of object.
     * @return http header.
     */
    public static <T> HttpHeaders generatePaginationHttpHeaders(UriComponentsBuilder uriBuilder, Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_X_TOTAL_COUNT, Long.toString(page.getTotalElements()));
        headers.add(HttpHeaders.LINK, linkHeaderUtil.prepareLinkHeaders(uriBuilder, page));
        return headers;
    }

    public static HttpHeaders getHeaders(Page<?> page) {

        HttpHeaders headers = new HttpHeaders() {

            private static final long serialVersionUID = -1058785881293603457L;

            {
                add("Access-Control-Expose-Headers", HEADER_X_TOTAL_COUNT);
                add(HEADER_X_TOTAL_COUNT, Long.toString(page.getTotalElements()));

                add("Access-Control-Expose-Headers", HEADER_X_PAGE_NUMBER);
                add(HEADER_X_PAGE_NUMBER, Integer.toString(page.getNumber()));

                add("Access-Control-Expose-Headers", HEADER_X_PAGE_SIZE);
                add(HEADER_X_PAGE_SIZE, Integer.toString(page.getSize()));

                add("Access-Control-Expose-Headers", HEADER_X_TOTAL_PAGE);
                add(HEADER_X_TOTAL_PAGE, Integer.toString(page.getTotalPages()));
            }
        };

        return headers;
    }
}
