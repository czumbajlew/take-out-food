package pl.kcit.tof.internal;

import pl.kcit.tof.configuration.exception.TofInternalException;

public interface Converter<FROM, TO> {

    TO convert(FROM from) throws TofInternalException;

}
