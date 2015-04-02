function Synchronizer(onStart, onFinish) {
    this.counter = 0;
    this.onStart = onStart;
    this.onFinish = onFinish;
}

Synchronizer.prototype.increment = function (value) {
    this.counter += value;
    console.log('sync started: ' + this.counter);
    this.onStart();
};

Synchronizer.prototype.decrement = function () {
    this.counter--;
    console.log('sync: ' + this.counter);
    if (this.counter == 0) {
        this.onFinish();
        console.log('sync finished');
    }
};