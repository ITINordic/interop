package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.Option;
import com.itinordic.interop.dhis.OptionSet;

/**
 *
 * @author Charles Chigoriwa
 */
public interface OptionSetService {

    public OptionSet getOptionSet();

    public Option getOption(String code);

}
