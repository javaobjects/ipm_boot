/**
 * 数字格式化
 */

 var numForUtil = function(value, unitType, is_hand) {
    switch(unitType) {
        case "num":
    	case "pps":
            if(value >= 100000000) {
                value = (value / 100000000).toFixed(2);
                unitType = "亿个";
            } else if(value >= 10000) {
                value = (value / 10000).toFixed(2);
                unitType = "万个";
            } else {
                value = value;
                unitType = "个";
            }
            break;
            
        case "ms":
            value = value.toFixed(2);
            unitType = "ms";
            break;
        case "flow":
            if (value < 1000) {
                value = value;
                unitType = "b";
            } else if (value >= 1000 && value < 1000000) {
                value = (value / 1000).toFixed(2);
                unitType = "Kb";
            } else if (value >= 1000000 && value < 1000000000) {
                value = (value / 1000000).toFixed(2);
                unitType = "Mb";
            } else if (value >= 1000000000 && value < 1000000000000) {
                value = (value / 1000000000).toFixed(2);
                unitType = "Gb";
            } else if (value >= 1000000000000) {
                value = (value / 1000000000000).toFixed(2);
                unitType = "Tb";
            }
            break;
        case "flowB":
            value = parseInt(value);
            unitType = "B";
            break;
            
        case "flowB1024":
            if (value < 1024) {
                value = value;
                unitType = "B";
            } else if (value >= 1024 && value < 1048576) {
                value = (value / 1024).toFixed(2);
                unitType = "KB";
            } else if (value >= 1048576 && value < 1073741824) {
                value = (value / 1048576).toFixed(2);
                unitType = "MB";
            } else if (value >= 1073741824) {
                value = (value / 1073741824).toFixed(2);
                unitType = "GB";
            }
            break;
            
        case "ratio":
            value = value.toFixed(2);
            unitType = "%";
            break;
    }
    return {
        value: value,
        unit: unitType
    };
 };
