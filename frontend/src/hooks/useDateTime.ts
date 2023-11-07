import { useState, useEffect } from "react";

const useDateTime = (dateTime: Date) => {
  const [date, setDate] = useState("");
  const [time, setTime] = useState("");

  useEffect(() => {
    const numToMonthMap = {
        0: "January",
        1: "February",
        2: "March",
        3: "April",
        4: "May",
        5: "June",
        6: "July",
        7: "August",
        8: "September",
        9: "October",
        10: "November",
        11: "December"
      };
  
      const hours = String(dateTime.getHours()).padStart(2, "0");
      const minutes = String(dateTime.getMinutes()).padStart(2, "0");
      const date = dateTime.getDate();
      const month = numToMonthMap[dateTime.getMonth() as keyof typeof numToMonthMap];
      const year = dateTime.getFullYear();
  
      setDate(`${date} ${month}, ${year}`);
      setTime(`${hours}:${minutes}`);
      
  }, [dateTime]);

  return { date, time };
};

export default useDateTime;
