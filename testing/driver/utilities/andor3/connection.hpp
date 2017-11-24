#include <cstdlib>
#include <memory>
#include <iostream>
#include "atcore.h"

#ifndef DRIVERTESTS_CONNECTION_HPP
#define DRIVERTESTS_CONNECTION_HPP

namespace andor3 {
    class Connection {
    private:
        int address_;
        std::auto_ptr<AT_H> connection_handle_;
        void InitializeAndorSdk();
        void FinalizeAndorSdk();
        void CloseSdkAndReset();
        inline void HandleAndorResultCode(int andor_result_code);
    public:
        Connection(int address);
        ~Connection();
        void Open();
        void Close();
        AT_H& ConnectionHandle();
    };
}


#endif //DRIVERTESTS_CONNECTION_HPP
