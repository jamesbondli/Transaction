package com.example.transaction.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseViewModel extends AndroidViewModel {

    /**
     * 基ViewModel继承AndroidViewModel，获得环境application
     *
     * @param application
     */
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 当ViewModel没有和任何Activity关联时系统会调用此方法
     * 可以在该方法中清理资源
     * 最好不要从外部传入Context,避免内存泄漏
     */
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
