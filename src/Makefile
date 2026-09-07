JAVAC = javac
JAVA = java

SOURCES = Main.java Cell.java Network.java FrequencyAllocator.java NetworkVisualiser.java
CLASSES = $(SOURCES:.java=.class)

.PHONY: all compile run clean

all: compile

compile:
	$(JAVAC) $(SOURCES)

run: compile
	$(JAVA) Main

clean:
	rm -f *.class Network.png