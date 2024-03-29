package com.sptek.webfw.config.filter.logbackFilter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;


public class LogbackKeywordFilterForXXX extends Filter<ILoggingEvent>{
    String filterKeyword = "XXX"; //LogBackFilter keywork example.

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getMessage().contains(filterKeyword)) {
            return FilterReply.ACCEPT;
        }else{
            return FilterReply.DENY;
        }
    }
}
