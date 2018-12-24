package org.apifever.raml.support;

public class ResolveResourceException extends RuntimeException
{
	private static final long serialVersionUID = -622721229000885162L;

	public ResolveResourceException(Throwable e)
    {
        super(e);
    }

	public ResolveResourceException(String msg) {
		super(msg);
	}

	public ResolveResourceException(String value, Throwable e) {
		super(value, e);
	}

}