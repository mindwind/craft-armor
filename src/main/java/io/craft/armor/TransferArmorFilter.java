package io.craft.armor;

import io.craft.armor.spi.ArmorInvocation;

/**
 * Transfer armor filter.
 * Transfer delegate object to link object.
 * 
 * @author mindwind
 * @version 1.0, Dec 31, 2014
 */
public class TransferArmorFilter extends AbstractArmorFilter {
	

	public TransferArmorFilter(ArmorContext context) {
		super(context);
	}

	@Override
	public void doFilter(ArmorInvocation invocation) throws Throwable {
		if (isOff(this.getClass())) { return; }
		
		Object delegateObject = invocation.getDelegateObject();
		Object transferObject = context.getTransferObject(delegateObject);
		if (transferObject == null) { return; }
		
		invocation.setDelegateObject(transferObject);
	}

}
