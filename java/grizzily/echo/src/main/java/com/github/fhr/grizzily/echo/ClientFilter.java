package com.github.fhr.grizzily.echo;

import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Fan Huaran
 * created on 2019/2/2
 * @description Client filter is responsible for redirecting server response to the standard output
 */
public class ClientFilter extends BaseFilter {

    private static final Logger logger = LoggerFactory.getLogger(ClientFilter.class);

    /**
     * Handle just read operation, when some message has come and ready to be
     * processed.
     *
     * @param ctx Context of {@link FilterChainContext} processing
     * @return the next action
     * @throws java.io.IOException
     */
    @Override
    public NextAction handleRead(final FilterChainContext ctx) throws IOException {
        // We get String message from the context, because we rely prev. Filter in chain is StringFilter
        final String serverResponse = ctx.getMessage();
        logger.info("Server echo: {}", serverResponse);

        return ctx.getStopAction();
    }
}
