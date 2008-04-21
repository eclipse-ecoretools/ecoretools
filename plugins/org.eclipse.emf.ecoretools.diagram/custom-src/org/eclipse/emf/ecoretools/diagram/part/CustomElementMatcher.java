package org.eclipse.emf.ecoretools.diagram.part;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;


public class CustomElementMatcher implements IElementMatcher {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.IElementMatcher#matches(org.eclipse.emf.ecore.EObject)
	 */
	public boolean matches(EObject object) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(object);
		if (domain == null)
		{
			return false;
		}
		String id = domain.getID();
		int endString = id.indexOf(EcoreDocumentProvider.id_separator);
		String domainBaseID = id.substring(0, endString);
		if (EcoreDocumentProvider.editingDomainBaseID.equals(domainBaseID)){
			return true;
		}
		return false;
	}

}
