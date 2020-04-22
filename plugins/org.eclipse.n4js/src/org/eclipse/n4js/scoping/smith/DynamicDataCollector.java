package org.eclipse.n4js.scoping.smith;

import java.util.ArrayDeque;

import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.ForwardingDataCollector;
import org.eclipse.n4js.smith.Measurement;

/**
 * Each invocation of getMeasurement() will create a new data collector in and push it to the current stack.
 */
class DynamicDataCollector extends ForwardingDataCollector {

	private final ArrayDeque<DataCollector> stack;

	/**
	 * Constructor
	 */
	protected DynamicDataCollector(DataCollector root) {
		super(root);
		stack = new ArrayDeque<>();
		stack.push(root);
	}

	@Override
	public Measurement getMeasurement(String name) {
		DataCollector parent = stack.peek();
		DataCollector child = DataCollectors.INSTANCE.getOrCreateDataCollector(name, parent);
		stack.push(child);
		class Popper implements Measurement {
			private final Measurement m;

			Popper(Measurement m) {
				this.m = m;
			}

			@Override
			public void close() {
				try {
					m.close();
				} finally {
					stack.pop();
				}
			}
		}
		return new Popper(child.getMeasurement());
	}

	@Override
	public Measurement getMeasurement() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Measurement getMeasurementIfInactive() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Measurement getMeasurementIfInactive(String name) {
		throw new UnsupportedOperationException();
	}

}
