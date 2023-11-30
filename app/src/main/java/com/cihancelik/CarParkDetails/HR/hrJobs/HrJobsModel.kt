package com.cihancelik.CarParkDetails.HR.hrJobs

import java.io.Serializable

data class HrJobsModel(
    var jobId: Int = 0,
    var jobName: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var updateDate: String = "",
    var creationDate: String = ""
):Serializable
