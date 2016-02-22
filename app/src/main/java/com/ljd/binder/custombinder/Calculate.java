package com.ljd.binder.custombinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by ljd-PC on 2016/2/21.
 */
public class Calculate extends Binder implements ICalculate{

    private final String TAG = "Calculate";
    private static final String DESCRIPTOR = "com.ljd.binder.ICalculate";

    @Override
    public void attachInterface(IInterface owner, String descriptor) {
        this.attachInterface(this, DESCRIPTOR);
    }
    public static ICalculate asInterface(IBinder obj) {
        if ((obj == null)) {
            return null;
        }
        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin != null) && (iin instanceof ICalculate))) {
            return (ICalculate) iin;
        }
        return new Proxy(obj);
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_add: {
                data.enforceInterface(DESCRIPTOR);
                int _arg0;
                _arg0 = data.readInt();
                int _arg1;
                _arg1 = data.readInt();
                int _result = this.add(_arg0, _arg1);
                reply.writeNoException();
                reply.writeInt(_result);
                return true;
            }
            case TRANSACTION_sub: {
                data.enforceInterface(DESCRIPTOR);
                int _arg0;
                _arg0 = data.readInt();
                int _arg1;
                _arg1 = data.readInt();
                int _result = this.sub(_arg0, _arg1);
                reply.writeNoException();
                reply.writeInt(_result);
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public int add(int first, int second) throws RemoteException {
        Log.e(TAG,String.valueOf(first+second));
        return first + second;
    }

    @Override
    public int sub(int first, int second) throws RemoteException {
        return first - second;
    }

    @Override
    public IBinder asBinder() {
        return null;
    }

    private static class Proxy implements ICalculate {
        private IBinder mRemote;

        Proxy(IBinder remote) {
            mRemote = remote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public int add(int first, int second) throws RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            int _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeInt(first);
                _data.writeInt(second);
                mRemote.transact(TRANSACTION_add, _data, _reply, 0);
                _reply.readException();
                _result = _reply.readInt();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        @Override
        public int sub(int first, int second) throws RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            int _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeInt(first);
                _data.writeInt(second);
                mRemote.transact(TRANSACTION_sub, _data, _reply, 0);
                _reply.readException();
                _result = _reply.readInt();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }
    }
}
