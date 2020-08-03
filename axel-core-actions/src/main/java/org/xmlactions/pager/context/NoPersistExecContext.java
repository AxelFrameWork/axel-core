package org.xmlactions.pager.context;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.ExecContext;

/**
 * Doesn't persist on server.
 * 
 * @author mikem
 *
 */
@SuppressWarnings("serial")
public class NoPersistExecContext extends ExecContext
{

//	private static final Logger log = LoggerFactory.getLogger(NoPersistExecContext.class);
	
	public NoPersistExecContext(List<Object> actionMaps, List<Object> localMaps, List<Object> themes) {
		super(actionMaps, localMaps, themes);
	}

	public NoPersistExecContext(List<Object> actionMaps, List<Object> localMaps) {
		super(actionMaps, localMaps, null);
	}

	public NoPersistExecContext(List<Object> actionMaps) {
		super(actionMaps, null, null);
	}

	@Override
	public void loadFromPersistence() {
	}

	@Override
	public void saveToPersistence() {
	}

	@Override
	public void reset() {
	}

}
