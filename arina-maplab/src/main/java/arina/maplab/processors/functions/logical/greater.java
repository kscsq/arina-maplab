package arina.maplab.processors.functions.logical;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;

public class greater extends MapLibraryFunctionProcessor
{
    public greater(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue op1 = computeInputParameter(0, context);
        IMapValue op2 = computeInputParameter(1, context);

        if(op1.isNotNull() && op2.isNotNull())
        {
            if (op1.getValue(BigDecimal.class).compareTo(op2.getValue(BigDecimal.class)) > 0)
                return MapValue.TRUE;
        }
        return MapValue.FALSE;
    }
}