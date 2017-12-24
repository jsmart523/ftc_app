package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Func;

/**
 * Created by John on 12/23/2017.
 */

public class Func_AbsoluteHeading implements Func {
    DriverControlRadialTest2 m_parent;

    public Func_AbsoluteHeading(DriverControlRadialTest2 parent) {
        m_parent = parent;
    }

    @Override
    public Object value() {
        return Math.round(m_parent.gyro.getHeading());
    }
}
