package com.device.business.evaluation.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.device.business.equip.bean.PileBean;
import com.device.business.equip.bean.PileBrokenBean;
import com.device.business.equip.dao.mapper.PileBeanMapper;
import com.device.business.equip.dao.mapper.PileBrokenBeanMapper;
import com.device.business.evaluation.bean.EvalBean;
import com.device.business.evaluation.bean.EvalEntireBean;
import com.device.business.evaluation.dao.mapper.EvalBeanMapper;
import com.device.business.evaluation.dao.mapper.EvalEntireBeanMapper;
import com.device.core.util.CommUtils;

public class EvaluationDao {

	@Autowired
	EvalBeanMapper mapper;

	@Autowired
	EvalEntireBeanMapper entireMapper;

	@Autowired
	PileBeanMapper pileMapper;

	@Autowired
	PileBrokenBeanMapper pileBrokenMapper;

	public List<EvalBean> selectAll() {
		List<EvalBean> list = mapper.selectAll();

		for (EvalBean bean : list) {
			bean.setEntires(entireMapper.selectByEvalId(bean.getId()));
		}
		return list;
	}

	public EvalBean selectById(int evalId) {
		return mapper.selectById(evalId);
	}

	public List<EvalEntireBean> getEntirsListByEvalId(int evalId) {
		return entireMapper.selectByEvalId(evalId);
	}

	public List<String> selectModel() {
		return mapper.selectModel();
	}

	//@Transactional
	public int eval(String pileId, EvalBean bean, List<EvalEntireBean> list)
			throws IllegalAccessException, InvocationTargetException {
		PileBean pile = pileMapper.selectById(pileId);
		PileBrokenBean broken = new PileBrokenBean();
		//CommUtils.copyProperties(broken, pile);

		float score = 0L;// this.evalScore(bean, list, pile);
		String scoreMark = null;
		
		if(null==pile.getPileLength()||0==pile.getPileLength().floatValue()){
			scoreMark = "桩长不合格";
			return mapper.updatePileScore((int) score, scoreMark,1, pileId);
		}

		float lengthScore = this.evalPileLength(bean, list, pile);
		if (0 == lengthScore) {
			scoreMark = "桩长不合格";
			//pileBrokenMapper.insert(broken);
			return mapper.updatePileScore((int) score, scoreMark,1, pileId);
		}

		float cementScore = this.evalCement(bean, list, pile);
		if (0 == cementScore) {
			scoreMark = "水泥用量不合格";
			//pileBrokenMapper.insert(broken);
			return mapper.updatePileScore((int) score, scoreMark,2, pileId);
		}

		float leanScore = this.evalLean(bean, list, pile);
		if (0 == leanScore) {
			scoreMark = "垂直度不合格";
			//pileBrokenMapper.insert(broken);
			return mapper.updatePileScore((int) score, scoreMark,3, pileId);
		}

		score = lengthScore + cementScore + leanScore;
		if (score < 60) {
			//pileBrokenMapper.insert(broken);
			return mapper.updatePileScore((int) score, "整体评分低",4, pileId);
		}

		return mapper.updatePileScore((int) score, "",0, pileId);
	}

	public int updateEvalById(EvalBean bean) {
		return mapper.updateEvalById(bean);
	}

	public int updateEvalEntityById(EvalEntireBean bean) {
		return entireMapper.updateById(bean);
	}

	public float evalScore(EvalBean eval, List<EvalEntireBean> entitys, PileBean pile) {
		float score = 0L;
		float lengthScore = this.evalPileLength(eval, entitys, pile);

		float cementScore = this.evalCement(eval, entitys, pile);
		float leanScore = this.evalLean(eval, entitys, pile);

		score = lengthScore + cementScore + leanScore;
		return score;
	}

	public float evalPileLength(EvalBean eval, List<EvalEntireBean> entitys, PileBean pile) {
		float score = 0L;
		float deviation=(pile.getPileLength().floatValue()-eval.getPilePlanLength())/eval.getPilePlanLength()*100L;

		if (eval.getId() == 1 || eval.getId() == 3) {
			if (deviation >=0L) {
				score = eval.getLengthScore() * 100;
			} else {
				deviation=Math.abs(deviation);
				for (EvalEntireBean entity : entitys) {
					if (entity.getEvaluationType() != 1) {
						continue;
					}

					if(entity.getPileLengthLess()!=0) {
                        if (deviation >= entity.getPileLengthMore() && deviation < entity.getPileLengthLess()) {
                            score = entity.getScore() * eval.getLengthScore();
                            break;
                        }
                    }
                    else{
                        if (deviation >= entity.getPileLengthMore()) {
                            score = 0L;
                            break;
                        }
                    }
				}
			}
		}
		if (eval.getId() == 2 || eval.getId() == 4) {
			float l = pile.getPileLength().floatValue();
			float A = pile.getMaxOutsidePower().floatValue();
			for (EvalEntireBean entity : entitys) {
				if (A >= entity.getPileLengthLess()) {
					if (null == entity.getElectricMore() || 0 == entity.getElectricMore()) {
						if (entity.getElectricLess() > A)
							score = entity.getScore() * eval.getLengthScore();
					}
					if (entity.getElectricLess() >= A && entity.getElectricMore() <= A) {
						score = entity.getScore() * eval.getLengthScore();
					}
					if (null == entity.getElectricLess() || 0 == entity.getElectricLess()) {
						if (entity.getElectricMore() < A)
							score = entity.getScore() * eval.getLengthScore();
					}
				}
			}
		}

		return score / 100;
	}

	public float evalCement(EvalBean eval, List<EvalEntireBean> entitys, PileBean pile) {
		float score = 0L;
		float actualPileCementDensity=pile.getTotalCement().floatValue() / pile.getPileLength().floatValue();
        System.out.println("actual cement density"+actualPileCementDensity);
		float deviation = (actualPileCementDensity-eval.getCementDensity())/eval.getCementDensity()*100L;
		System.out.println("cement deviation"+deviation);
		if (deviation >= 0L) {
			score = eval.getCementScore() * 100;
		} else {
            deviation=Math.abs(deviation);
			for (EvalEntireBean entity : entitys) {
				if (entity.getEvaluationType() != 2) {
					continue;
				}

                if(entity.getCementLess()!=0) {
                    if (deviation >= entity.getCementMore()&& deviation < entity.getCementLess()) {
                        score = entity.getScore() * eval.getCementScore();
                        break;
                    }
                }
                else{
                    if (deviation >= entity.getCementMore()) {
                        score = 0L;
                        break;
                    }
                }
			}
		}
		return score / 100;
	}

	public float evalLean(EvalBean eval, List<EvalEntireBean> entitys, PileBean pile) {
		float score = 0L;
		float actualPileMaxLean=Math.abs(pile.getMaxLean().floatValue());
		if (actualPileMaxLean ==0L) {
			score = eval.getLeanScore() * 100;
		} else {
			for (EvalEntireBean entity : entitys) {
				if (entity.getEvaluationType() != 3) {
					continue;
				}

                if(entity.getLeanLess()!=0) {
                    if (actualPileMaxLean >= entity.getLeanMore()&& actualPileMaxLean < entity.getLeanLess()) {
                        score = entity.getScore() * eval.getLeanScore();
                        break;
                    }
                }
                else{
                    if (actualPileMaxLean >= entity.getLeanMore()) {
                        score = 0L;
                        break;
                    }
                }
			}
		}
		return score / 100;
	}
}
