package org.xmlactions.pager.context;


import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.ExecContext;

@SuppressWarnings("serial")
public class SessionExecContext extends ExecContext
{

	private static final Logger log = LoggerFactory.getLogger(SessionExecContext.class);

	HttpSession session;

	public SessionExecContext(List<Object> actionMaps, List<Object> localMaps, List<Object> themes) {
		super(actionMaps, localMaps, themes);
	}

	public SessionExecContext(List<Object> actionMaps, List<Object> localMaps) {
		super(actionMaps, localMaps, null);
	}

	public SessionExecContext(List<Object> actionMaps) {
		super(actionMaps, null, null);
	}

	public void setSession(HttpSession session)
	{

		this.session = session;
	}

	/**
	 * Load all persistence values from httpSession to execContext
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void loadFromPersistence()
	{
		Enumeration<String> enumeration = session.getAttributeNames();
		if (log.isDebugEnabled()) {
			log.debug("loadFromPersistence.session:");
			log.debug("isNew:" + session.isNew());
			log.debug("getId:" + session.getId());
		}
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			Object value = session.getAttribute(key);
			persist(key, value);
			log.info("loadFromPersistence key:" + key + " value:" + value);
		}
	}

	/**
	 * Save all persistence values from execContext to httpSession
	 * 
	 */
	public void saveToPersistence()
	{

		Map<String, Object> persistenceMap = getPersistenceMap();
		for (String key : persistenceMap.keySet()) {
			Object value = persistenceMap.get(key);
			session.setAttribute(key, value);
			log.info("saveToPersistence key:" + key + " value:" + value);
		}
	}

    /**
     * Clear all persistent variables from execContext and HttpSession
     */
	public void reset()
	{
        Map<String, Object> map = getPersistenceMap();
        for (String key : map.keySet()) {
            session.removeAttribute(key);
            if (log.isDebugEnabled()) {
                log.debug("Session attribute [" + key + "] cleared.");
            }
        }
        getPersistenceMap().clear();
        log.debug("persistenceMap cleared");
	}

}
