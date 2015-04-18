class FeedsController < ApplicationController
	def create
    	@event = Event.find(params[:event_id])
    	@feed = @event.feeds.new(feed_params)
    	@feed.user_id = current_user.id
    	connection = Connection.where(used: false).first
    	@feed.connection_id = connection.id
    	connection.used = true
    	connection.save
    	@feed.save

    	@stream = @feed.streams.new()
    	@stream.save
    	redirect_to event_path(@event)
	end
 
  private
   	def feed_params
   		params.require(:feed).permit(:title)
   	end
end
