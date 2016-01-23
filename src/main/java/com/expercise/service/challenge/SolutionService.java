package com.expercise.service.challenge;

import com.expercise.controller.challenge.model.UserSolutionModel;
import com.expercise.dao.challenge.SolutionDao;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.challenge.Solution;
import com.expercise.domain.level.Level;
import com.expercise.domain.user.User;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.service.user.AuthenticationService;
import com.expercise.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SolutionService {

    @Autowired
    private SolutionDao solutionDao;

    @Autowired
    private AuthenticationService authenticationService;

    @Transactional
    public void saveSolution(Solution solution) {
        solutionDao.save(solution);
    }

    public Solution getSolutionBy(Challenge challenge, User user, ProgrammingLanguage programmingLanguage) {
        return solutionDao.findBy(challenge, user, programmingLanguage);
    }

    public List<Solution> getAllSolutionsInLevelsOf(User user, List<Level> levels) {
        if (levels.isEmpty()) {
            return Collections.emptyList();
        }
        return solutionDao.findAllSolutionsInLevelsOf(user, levels);
    }

    public void updateSolution(Solution solution) {
        solutionDao.update(solution);
    }

    public long getSolutionCountOf(Challenge challenge) {
        return solutionDao.countByChallenge(challenge);
    }

    public List<UserSolutionModel> getUserSolutionModels(Challenge challenge) {
        return getSolutionsOfUser(challenge).stream()
                .map(UserSolutionModel::createFrom)
                .sorted((o1, o2) ->
                        DateUtils.toDateTimeWithNamedMonth(o2.getSolutionDate()).compareTo(DateUtils.toDateTimeWithNamedMonth(o1.getSolutionDate())))
                .collect(Collectors.toList());
    }

    public Set<Challenge> getSolvedChallengesOf(User user) {
        return solutionDao.findApprovedChallengeSolutionsByUser(user).stream()
                .map(Solution::getChallenge)
                .collect(Collectors.toSet());
    }

    public Set<Challenge> getSolvedChallengesOfCurrentUser() {
        return getSolvedChallengesOf(authenticationService.getCurrentUser());
    }

    private List<Solution> getSolutionsOfUser(Challenge challenge) {
        User currentUser = authenticationService.getCurrentUser();
        return solutionDao.findSolutionsBy(challenge, currentUser);
    }

}