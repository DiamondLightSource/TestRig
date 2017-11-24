#include "connection.hpp"

andor3::Connection::Connection(int address) :
    address_(address),
    connection_handle_(new AT_H(AT_HANDLE_UNINITIALISED))
    {
        InitializeAndorSdk();
    }

andor3::Connection::~Connection() {
    Close();
    FinalizeAndorSdk();
}

void andor3::Connection::Open() {
    Close();
    int andor_result_code = AT_Open(address_, &*connection_handle_);
    HandleAndorResultCode(andor_result_code);
}

void andor3::Connection::InitializeAndorSdk() {
    int andor_result_code = AT_InitialiseLibrary();
    HandleAndorResultCode(andor_result_code);
}

void andor3::Connection::Close() {
    if(*connection_handle_ != AT_HANDLE_UNINITIALISED)
        CloseSdkAndReset();
}

void andor3::Connection::CloseSdkAndReset() {
    int andor_result_code = AT_Close(*connection_handle_);
    HandleAndorResultCode(andor_result_code);
    *connection_handle_ = AT_HANDLE_UNINITIALISED;
}

void andor3::Connection::FinalizeAndorSdk() {
    int andor_result_code = AT_FinaliseLibrary();
    HandleAndorResultCode(andor_result_code);
}

inline void andor3::Connection::HandleAndorResultCode(int andor_result_code) {
    if(andor_result_code)
        throw andor_result_code;
}

AT_H& andor3::Connection::ConnectionHandle() {
    return *connection_handle_;
}