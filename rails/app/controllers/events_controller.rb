class EventsController < ApplicationController

	require 'socket'

	def index
		@events = Event.all

		s = TCPSocket.new 'localhost', 11111
		s.puts "hello"
		s.puts "world"
		puts s.gets
		s.close

		#uri = URI('http://localhost:11111')
		#req = Net::HTTP::Post.new(uri)
		#req.set_form_data('from' => '2005-01-01', 'to' => '2005-03-31')

		#res = Net::HTTP.start(uri.hostname, uri.port) do |http|
		#  http.request(req)
		#end

		#case res
		#when Net::HTTPSuccess, Net::HTTPRedirection
		  # OK
		#else
		#  res.value
		#end
	end

	def show
    	@event = Event.find(params[:id])
	end

	def new
		@event = Event.new
	end

	def edit
		@event = Event.find(params[:id])
	end

	def create
		@event = Event.new(event_params)
 
	 	if @event.save
    		redirect_to @event
		else
    		render 'new'
		end
	end

	def update
		@event = Event.find(params[:id])
 
		if @event.update(event_params)
			redirect_to @event
		else
			render 'edit'
		end
	end

	def destroy
		@event = Event.find(params[:id])
		@event.destroy
 
		redirect_to events_path
	end

	private
	 	def event_params
	    	params.require(:event).permit(:title, :description)
		end
end
