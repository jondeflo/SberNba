package ru.sbernba.interntask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.sbernba.interntask.model.Discipline;
import ru.sbernba.interntask.model.StudentRate;
import ru.sbernba.interntask.repository.DisciplineRepository;
import ru.sbernba.interntask.repository.RateRepository;
import ru.sbernba.interntask.repository.StudentRateRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class StatService {

	@Autowired
	StudentRateRepository studentRateRepository;

	@Autowired
	DisciplineRepository disciplineRepository;

	@Autowired
	RateRepository rateRepository;

	public ResponseEntity<Object> calculateStat() {

		Map<String, String> result = new LinkedHashMap<>();

		int year = Calendar.getInstance().get(Calendar.YEAR);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter.parse(year + "-09-01 00:00:00");
			endDate = formatter.parse((year + 1) + "-06-30 23:59:59");
		} catch (ParseException e) {
			return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Timestamp start = new Timestamp(startDate.getTime());
		Timestamp end = new Timestamp(endDate.getTime());

		ArrayList<Discipline> disciplines = disciplineRepository.findBy();

		countMediumScore(start, end, disciplines, result);
		countLowestAndHighestScore(start, end, disciplines, result);
		countTotalScores(start, end, result);

		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	private void countMediumScore(Timestamp start, Timestamp end, ArrayList<Discipline> disciplines, Map<String, String> result) {

		for (Discipline d : disciplines) {
			ArrayList<StudentRate> ratesList = studentRateRepository.findAllByTimestampBetweenAndDisciplineId(start, end, d.getId());
			float medium = 0;
			for (StudentRate r : ratesList)
				medium += r.getRate().getRate();
			result.put("Average " + d.getName() + " score", medium > 0 ? String.valueOf(medium/ratesList.size()) : String.valueOf(0) );
		}
	}

	private void countLowestAndHighestScore(Timestamp start, Timestamp end, ArrayList<Discipline> disciplines, Map<String, String> result) {

		for (Discipline d : disciplines) {
			ArrayList<StudentRate> ratesList = studentRateRepository.findAllByTimestampBetweenAndDisciplineId(start, end, d.getId());
			int lowest = Integer.MAX_VALUE;
			int highest = Integer.MIN_VALUE;
			for (StudentRate r : ratesList) {
				int tmp = r.getRate().getRate();
				if (tmp < lowest) {
					lowest = tmp;
				}
				if (tmp > highest) {
					highest = tmp;
				}
			}
			result.put("Lowest " + d.getName() + " score", lowest == Integer.MAX_VALUE ? String.valueOf(0) : String.valueOf(lowest));
			result.put("Highest " + d.getName() + " score", highest == Integer.MIN_VALUE ? String.valueOf(0) : String.valueOf(highest));
		}
	}

	private void countTotalScores(Timestamp start, Timestamp end, Map<String, String> result) {

		int[] count = new int[4];

		ArrayList<StudentRate> list = studentRateRepository.findAllByTimestampBetweenOrderByTimestampAsc(start, end);

		for (StudentRate r : list) {
			int tmp = r.getRate().getRate();

			switch (tmp) {
				case 2: count[0]++;
					break;
				case 3: count[1]++;
					break;
				case 4: count[2]++;
					break;
				case 5: count[3]++;
					break;
			}
			result.put("Total amount of 2's", String.valueOf(count[0]));
			result.put("Total amount of 3's", String.valueOf(count[1]));
			result.put("Total amount of 4's", String.valueOf(count[2]));
			result.put("Total amount of 5's", String.valueOf(count[3]));
		}

		countBestSeries(list, result);
	}

	private void countBestSeries(ArrayList<StudentRate> list, Map<String, String> result) {

		for (int rank = 5; rank > 2; rank--) {

			int maxLength = 0;
			int curLength = 1;

			for (int i = 0; i < list.size() - 1; i++) {
				if (list.get(i).getRate().getRate() == rank && list.get(i + 1).getRate().getRate() == rank) {
					curLength++;
					if (curLength > maxLength) {
						maxLength = curLength;
					}
				} else {
					curLength = 1;
				}
			}
			if (maxLength > 1) {
				result.put("The longest sequence of " + rank + "'s", String.valueOf(maxLength));
				return;
			}
		}

		result.put("The longest sequence of highest rank ", String.valueOf(0));
	}
}
