TARGET_FN="driver-tests"

SRC=main.cpp


VERBOSE = 0

LINK = $(CXX)

CPPFLAGS += -x c++ -g -c $(EXTRAFLAGS) $(INCLUDE)

LIBS+= -latcore

OBJ_DIR=./

OBJS = $(SRC:%.cpp=%.o)

$(TARGET_FN) : $(OBJS)
	$(LINK) $(EXTRAFLAGS) $(LFLAGS) -o $(TARGET_FN) $(OBJS) $(LIBS)

%.o: %.cpp
	@echo "Compiling $< ..."
	@if [ $(VERBOSE) -eq 1 ] ; then \
	  echo $(CXX) -o $@ $(CPPFLAGS) $< ; \
	fi ; \
	$(CXX) -o $@ $(CPPFLAGS) $<

clean:
	@echo "Cleaning..."
	@echo "Removing $(OBJS)"
	@-rm -rf $(OBJS)
	@echo
	@echo "Removing $(TARGET_FN)"
	@-rm -rf $(TARGET_FN)

