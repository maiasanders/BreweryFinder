import router from "../router";

function axiosErrorHandling(error) {
    // TODO replace window.alert w/ a modal?
    if (!error.response) {
        return;
    }
    const status = error.response.status
    if (status === 400) {
        window.alert('We are unable to process your request, check your inputs and try again.')
    } else if (status === 401) {
        router.go('/login')
    } else if (status === 403) {
        window.alert("Looks like you don't have access to this page!")
        history.back()
    } else if (status === 404) {
        return status;
    } else if (status >= 500) {
        window.alert("We're having trouble on our end, please try again later!")
    } else {
        window.alert("Looks like we're having some problems, please try again later.")
    }
}

function convertTimeToInputFormat(time) {
    if (time.includes('AM')) {
        return time.slice(0, 5)
    } else {
        let hrs = Number(time.slice(0, 2)) + 12;
        return `${hrs}:${time.slice(3, 5)}`
    }
}


function convertInputToString(time) {
    let hrs = Number(time.slice(0, 2));
    let mins = Number(time.slice(3, 5));
    let amPm = ''
    if (hrs === 0) {
        hrs = 12;
        amPm = "AM";
    } else if (hrs < 12) {
        amPm = 'PM'
    } else if (hrs >= 12) {
        amPm = 'PM'
        if (hrs > 12) hrs -= 12
    }
    return `${hrs < 10 ? '0' + hrs : hrs}:${mins < 10 ? '0' + mins : mins} ${amPm}`
}

function formatStringForSql(str) {
    return str.replaceAll("'", "''")
}

export { 
    axiosErrorHandling,
    convertInputToString, 
    convertTimeToInputFormat,
    formatStringForSql
}