#include "connection.hpp"

Connection::Connection(int address) :
    address_(address),
    connection_handle_(new AT_H(AT_HANDLE_UNINITIALISED))
    {
        InitializeAndorSdk();
    }

Connection::~Connection() {
    Close();
    FinalizeAndorSdk();
}

void Connection::Open() {
    Close();
    int andor_result_code = AT_Open(address_, &*connection_handle_);
    HandleAndorResultCode(andor_result_code);
}

void Connection::InitializeAndorSdk() {
    int andor_result_code = AT_InitialiseLibrary();
    HandleAndorResultCode(andor_result_code);
}

void Connection::Close() {
    if(*connection_handle_ != AT_HANDLE_UNINITIALISED)
        CloseSdkAndReset();
}

void Connection::CloseSdkAndReset() {
    int andor_result_code = AT_Close(*connection_handle_);
    HandleAndorResultCode(andor_result_code);
    *connection_handle_ = AT_HANDLE_UNINITIALISED;
}

void Connection::FinalizeAndorSdk() {
    int andor_result_code = AT_FinaliseLibrary();
    HandleAndorResultCode(andor_result_code);
}

inline void Connection::HandleAndorResultCode(int andor_result_code) {
    if(andor_result_code)
        throw andor_result_code;
        //std::cout << "Andor SDK error " << andor_result_code << std::endl;
}

AT_H& Connection::ConnectionHandle() {
    return *connection_handle_;
}