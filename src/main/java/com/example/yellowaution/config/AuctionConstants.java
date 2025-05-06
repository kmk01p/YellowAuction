package com.example.yellowaution.config;

/**
 * AuctionConstants 클래스는 경매(입찰) 시스템 전반에서 사용되는
 * 주요 상수값을 한 곳에 모아둔 설정 클래스입니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>경매 등록 및 검증 로직에서 최소 시작 금액을 강제</li>
 *   <li>프리랜서 입찰 시 한 번에 올릴 수 있는 최소 단위를 정의</li>
 * </ul>
 * </p>
 *
 * <p>이 클래스에 정의된 값은 서비스 로직(AuctionService 등)이나
 * 컨트롤러(AuctionController)에서 참조하여 사용합니다.</p>
 */
public class AuctionConstants {

    /**
     * 경매(구인) 시작 최소 금액 (단위: 원)
     *
     * <p>설명:
     * - 프로젝트 의뢰인이 경매를 등록할 때 설정할 수 있는 시작가의 최솟값입니다.
     * - 사용자 피드백을 통해 현재 100,000원(10만원)으로 조정되었습니다.</p>
     *
     * <p>사용 예시:
     * <code>if (startBid < AuctionConstants.MINIMUM_STARTING_BID) {
     *     throw new InvalidBidException("시작가는 " + MINIMUM_STARTING_BID + "원 이상이어야 합니다.");
     * }</code>
     * </p>
     */
    public static final long MINIMUM_STARTING_BID = 100_000L;

    /**
     * 프리랜서 입찰 시 최소 단위 (단위: 원)
     *
     * <p>설명:
     * - 프리랜서는 기존 최고 입찰가에 이 값만큼 더 올린 금액부터
     *   새로운 입찰을 할 수 있습니다.
     * - 단위가 너무 작으면 과도한 트래픽과 자잘한 입찰 경쟁이 발생하므로,
     *   합리적인 경쟁 유도를 위해 일정 수준(예: 1만 원)으로 고정합니다.</p>
     *
     * <p>사용 예시:
     * <code>
     * long nextMinBid = currentHighestBid + AuctionConstants.FREELANCER_BID_INCREMENT;
     * if (newBid < nextMinBid) {
     *     throw new InvalidBidException("입찰가는 최소 " +
     *         AuctionConstants.FREELANCER_BID_INCREMENT +
     *         "원 단위로, 현재가(" + currentHighestBid + "원)보다 높아야 합니다.");
     * }
     * </code>
     * </p>
     */
    public static final long FREELANCER_BID_INCREMENT = 10_000L;
}
