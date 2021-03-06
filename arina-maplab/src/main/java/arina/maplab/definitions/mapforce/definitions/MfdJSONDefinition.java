package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.mapforce.model.Entry;
import arina.maplab.processors.IMapComponentProcessor;
import arina.maplab.processors.MapJSONProcessor;
import arina.utils.FieldDef;
import arina.utils.TypesUtils;
import java.util.LinkedHashMap;

public class MfdJSONDefinition extends MfdVariableDefinition
{
    boolean first = true;

    @Override
    protected String getEntryName(String path, Entry entry)
    {
        return path;
    }

    @Override
    protected String addPath(String path, Entry entry) throws Exception
    {
        if("json-property".equals(entry.getType()))
            return ("/".equals(path) ? "" : path) + "/" + entry.getName();
        else
            return path;
    }

    @Override
    protected boolean addFieldDef(String path, Entry entry) throws Exception
    {
        if("json-property".equals(entry.getType()) || ("/".equals(path) && "root".equals(entry.getName())))
        {
             if(entry.getEntry().size() > 0)
            {
                String name = entry.getEntry().get(0).getName();
                if ("array".equals(name) &&
                        entry.getEntry().get(0).getEntry().size() > 0 &&
                        "json-item".equals(entry.getEntry().get(0).getEntry().get(0).getType()))
                {
                    if(entry.getEntry().get(0).getEntry().get(0).getEntry().size() > 0)
                    {
                        name = entry.getEntry().get(0).getEntry().get(0).getEntry().get(0).getName();
                        if ("object".equals(name))
                        {
                            fields.put(path, new FieldDef(LinkedHashMap.class, true, false));
                            return true;
                        }
                        else if ("array".equals(name))
                        {
                            // nothing
                        }
                        else
                        {
                            fields.put(path, new FieldDef(TypesUtils.getClass(name), true, false));
                            return true;
                        }
                    }
                }
                else if ("object".equals(name))
                {
                    fields.put(path, new FieldDef(LinkedHashMap.class, false, false));
                    return true;
                }
                else if ("array".equals(name))
                {
                    // nothing
                }
                else
                {
                    fields.put(path, new FieldDef(TypesUtils.getClass(name), false, false));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected String correctInstancePath(String path)
    {
        return path.equals("/0") ? path : path.substring(2);
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return new MapJSONProcessor(this, connectors, fields, usageKind, componentInput, componentOutput);
    }
}

