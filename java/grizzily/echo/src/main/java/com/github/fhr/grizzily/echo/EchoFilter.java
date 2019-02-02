package com.github.fhr.grizzily.echo;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Fan Huaran
 * created on 2019/2/2
 * @description Implementation of {@link FilterChain} filter, which replies with the request
 * * message.
 */
public class EchoFilter extends BaseFilter {

    private static final Logger logger = LoggerFactory.getLogger(EchoFilter.class);

    /**
     * Handle just read operation, when some message has come and ready to be
     * processed.
     *
     * @param ctx Context of {@link FilterChainContext} processing
     * @return the next action
     * @throws java.io.IOException
     */
    @Override
    public NextAction handleRead(FilterChainContext ctx) throws IOException {
        // Peer address is used for non-connected UDP Connection :)
        final Object peerAddress = ctx.getAddress();

        final Object message = ctx.getMessage();

        logger.info("receive client msg, client:{}, msg:{}", peerAddress, message);

        ctx.write(peerAddress, message, new CompletionHandler<WriteResult>() {
            @Override
            public void cancelled() {
                logger.info("send client msg cancelled, client:{}, msg:{}", peerAddress, message);
            }

            @Override
            public void failed(Throwable throwable) {
                logger.error("send client msg failed, client:{}, msg:{}", peerAddress, message, throwable);

            }

            @Override
            public void completed(WriteResult writeResult) {
                logger.info("send client msg completed, client:{}, msg:{}", peerAddress, message);

            }

            @Override
            public void updated(WriteResult writeResult) {
                logger.info("send client msg process update, client:{}, msg:{}", peerAddress, message);

            }
        });

        return ctx.getStopAction();
    }
}
