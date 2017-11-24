//
// Created by vid18871 on 24/11/17.
//

#include "andor3/connection.hpp"

#ifndef DRIVERTESTS_CAMERA_HPP
#define DRIVERTESTS_CAMERA_HPP

namespace andor3 {
    typedef int CameraAddress;

    class Camera {
    private:
        Connection camera_connection_;
    public:
        Camera(CameraAddress camera_address);
        ~Camera();
    };
}


#endif //DRIVERTESTS_CAMERA_HPP
