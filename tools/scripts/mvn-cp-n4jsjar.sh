#/bin/bash
cd ../..
# First, run Maven to create n4jsc.jar
echo "Running maven build"
mvn clean verify -DnoTests -PbuildProduct

# Create a target folder in /tests/org.eclipse.n4js.hlc.tests/
if [ ! -d "./tests/org.eclipse.n4js.hlc.tests/target" ]; then
	echo "Creating folder ./tests/org.eclipse.n4js.hlc.tests/target"
	mkdir "./tests/org.eclipse.n4js.hlc.tests/target"
fi

# Copy n4jsc.jar "./tests/org.eclipse.n4js.hlc.tests/target"
echo "Copy n4jsc.jar to /tests/org.eclipse.n4js.hlc.tests/target"
cp target/n4jsc.jar ./tests/org.eclipse.n4js.hlc.tests/target/
echo "You can run tests in /tests/org.eclipse.n4js.hlc.tests now."
