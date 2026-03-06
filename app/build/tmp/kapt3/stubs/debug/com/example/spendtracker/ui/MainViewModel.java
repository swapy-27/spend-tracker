package com.example.spendtracker.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u000e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001e\u001a\u00020\u0017J\u0006\u0010\u001f\u001a\u00020\u0017J\u0006\u0010 \u001a\u00020\u0017R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/example/spendtracker/ui/MainViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_dashboardUiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/spendtracker/ui/DashboardUiState;", "_thresholdUiState", "Lcom/example/spendtracker/ui/ThresholdUiState;", "appContext", "Landroid/content/Context;", "dashboardUiState", "Lkotlinx/coroutines/flow/StateFlow;", "getDashboardUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "thresholdRepository", "Lcom/example/spendtracker/repository/ThresholdRepository;", "thresholdUiState", "getThresholdUiState", "transactionRepository", "Lcom/example/spendtracker/repository/TransactionRepository;", "observeThresholds", "", "onCustomMessageChanged", "value", "", "onDailyThresholdChanged", "onMonthlyThresholdChanged", "onWeeklyThresholdChanged", "refreshSpending", "saveThresholds", "syncSms", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context appContext = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.spendtracker.repository.TransactionRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.spendtracker.repository.ThresholdRepository thresholdRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.spendtracker.ui.DashboardUiState> _dashboardUiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.spendtracker.ui.DashboardUiState> dashboardUiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.spendtracker.ui.ThresholdUiState> _thresholdUiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.spendtracker.ui.ThresholdUiState> thresholdUiState = null;
    
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.spendtracker.ui.DashboardUiState> getDashboardUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.spendtracker.ui.ThresholdUiState> getThresholdUiState() {
        return null;
    }
    
    private final void observeThresholds() {
    }
    
    public final void refreshSpending() {
    }
    
    public final void syncSms() {
    }
    
    public final void onDailyThresholdChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onWeeklyThresholdChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onMonthlyThresholdChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onCustomMessageChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void saveThresholds() {
    }
}